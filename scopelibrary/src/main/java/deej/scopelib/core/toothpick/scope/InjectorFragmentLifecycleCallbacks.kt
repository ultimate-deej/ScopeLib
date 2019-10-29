package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import toothpick.Scope
import toothpick.Toothpick
import toothpick.ktp.extension.getInstance

class InjectorFragmentLifecycleCallbacks(
    private val associatedContainerScopeOptions: ScopeOptions,
    private val log: Boolean = false
) : FragmentManager.FragmentLifecycleCallbacks() {

    init {
        restoreExtensions()
    }

    private fun restoreExtensions() {
        var node = associatedContainerScopeOptions
        var extension = associatedContainerScopeOptions.extension
        if (extension != null && Toothpick.isScopeOpen(extension.name)) return

        while (extension != null) {
            Toothpick.openScopes(node.name, extension.name)
                .installModules(*extension.scopeArguments.createModules(), ScopeOptionsModule(extension))

            node = extension
            extension = extension.extension
        }
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        println("QWE CREATING F ${f.javaClass.simpleName}")
        if (f !is OpensScope) {
            Toothpick.openScope(associatedContainerScopeOptions.name)
                .inject(f)
            return
        }

        val scopeOptionsInFragment = f.scopeOptions

        if (isSameAsLive(scopeOptionsInFragment)) {
            println("QWE SCOPE (${scopeOptionsInFragment.name}) ALREADY OPEN, VALID, SKIPPING")
            Toothpick.openScope(scopeOptionsInFragment.name)
                .inject(f)
        } else {
            if (Toothpick.isScopeOpen(scopeOptionsInFragment.name)) {
                val liveId = Toothpick.openScope(scopeOptionsInFragment.name).getInstance<ScopeOptions>().instanceId
                println("QWE SCOPE (${scopeOptionsInFragment.name}) INVALID, CLOSING $liveId")
            }
            Toothpick.closeScope(scopeOptionsInFragment.name)
            val oldHeadName = associatedContainerScopeOptions.head.name
            associatedContainerScopeOptions.removeExtension(scopeOptionsInFragment.name, null)
            if (oldHeadName != associatedContainerScopeOptions.head.name) {
                println("QWE HEAD CUT DOWN FROM ($oldHeadName) TO (${associatedContainerScopeOptions.head.name})")
            }
            initializeScope(scopeOptionsInFragment)
                .inject(f)
            if (scopeOptionsInFragment.extends) {
                associatedContainerScopeOptions.extend(scopeOptionsInFragment)
            }
        }
    }

    private fun isSameAsLive(scopeOptions: ScopeOptions): Boolean {
        if (!Toothpick.isScopeOpen(scopeOptions.name)) return false

        val liveScopeOptions: ScopeOptions = Toothpick.openScope(scopeOptions.name).getInstance()
        return scopeOptions.instanceId == liveScopeOptions.instanceId
    }

    private fun initializeScope(scopeOptions: ScopeOptions): Scope {
        val parentName = associatedContainerScopeOptions.head.name
        return Toothpick.openScopes(parentName, scopeOptions.name)
            .installModules(*scopeOptions.scopeArguments.createModules(), ScopeOptionsModule(scopeOptions))
            .also {
                println("QWE SCOPE NEWLY OPENED ${scopeOptions.instanceId} (${scopeOptions.name}) parent ($parentName)")
            }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is OpensScope) return
        if (f.isStateSaved) return

        val scopeOptionsInFragment = f.scopeOptions
        println("QWE DESTROY REQUESTED FOR ${scopeOptionsInFragment.instanceId} (${scopeOptionsInFragment.name})")
        if (isSameAsLive(scopeOptionsInFragment)) {
            println("QWE CLOSING (${scopeOptionsInFragment.name})")
            Toothpick.closeScope(scopeOptionsInFragment.name)
            val oldHeadName = associatedContainerScopeOptions.head.name
            associatedContainerScopeOptions.removeExtension(scopeOptionsInFragment.name, scopeOptionsInFragment.instanceId)
            if (oldHeadName != associatedContainerScopeOptions.head.name) {
                println("QWE HEAD CUT DOWN FROM ($oldHeadName) TO ${associatedContainerScopeOptions.head.name}")
            }
        } else {
            println("QWE KEEPING (${scopeOptionsInFragment.name})")
        }
    }

    override fun toString(): String {
        val scopeChainString = StringBuilder(associatedContainerScopeOptions.name.toString())
        var extension = associatedContainerScopeOptions.extension
        while (extension != null) {
            scopeChainString.append(extension.name, ", ")
            extension = extension.extension
        }
        return "InjectorFragmentLifecycleCallbacks (scopes=[$scopeChainString])"
    }

    private fun println(message: String) {
        if (!log) return
        kotlin.io.println(message)
    }
}
