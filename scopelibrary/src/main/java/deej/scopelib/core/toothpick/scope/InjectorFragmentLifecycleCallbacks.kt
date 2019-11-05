package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import toothpick.Scope
import toothpick.Toothpick
import toothpick.ktp.extension.getInstance

class InjectorFragmentLifecycleCallbacks(
    private val containerScopeOptions: ScopeOptions,
    private val log: Boolean = false
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        println("QWE CREATING F ${f.javaClass.simpleName}")
        if (f !is OpensScope) {
            Toothpick.openScope(containerScopeOptions.tail.name)
                .inject(f)
            return
        }

        with(f.scopeOptions) {
            ensureScopes(this)
            Toothpick.openScope(name)
                .inject(f)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is OpensScope) return
        if (f.isStateSaved) return

        val scopeOptionsInFragment = f.scopeOptions
        println("QWE DESTROY REQUESTED FOR ${scopeOptionsInFragment.instanceId} (${scopeOptionsInFragment.name})")

        if (isSameAsLive(scopeOptionsInFragment) == true) {
            println("QWE CLOSING (${scopeOptionsInFragment.name})")

            Toothpick.closeScope(scopeOptionsInFragment.name)
            val oldTailName = containerScopeOptions.tail.name

            containerScopeOptions.removeDescendant(scopeOptionsInFragment.name, scopeOptionsInFragment.instanceId)

            if (oldTailName != containerScopeOptions.tail.name) {
                println("QWE TAIL CUT DOWN FROM 3 ($oldTailName) TO ${containerScopeOptions.tail.name}")
            }
        } else {
            println("QWE KEEPING (${scopeOptionsInFragment.name})")
        }
    }

    private fun ensureScopes(scopeOptions: ScopeOptions) {
        val sameAsLive = isSameAsLive(scopeOptions)
        if (sameAsLive == false) {
            val liveId = Toothpick.openScope(scopeOptions.name).getInstance<ScopeOptions>().instanceId
            println("QWE SCOPE (${scopeOptions.name}) INVALID, CLOSING $liveId")
            Toothpick.closeScope(scopeOptions.name)
            val oldTailName = containerScopeOptions.tail.name
            // BEFORE and AFTER - I'm just trying to understand what's wrong after process death
            println("QWE CHILD BEFORE ${scopeOptions.child}")
            containerScopeOptions.removeDescendant(scopeOptions.name, null)
            println("QWE CHILD AFTER ${scopeOptions.child}")
            if (oldTailName != containerScopeOptions.tail.name) {
//                Debug.waitForDebugger()
                println("QWE TAIL CUT DOWN FROM 1 ($oldTailName) TO (${containerScopeOptions.tail.name})")
            }
        }
        if (sameAsLive == null) {
            println("QWE SCOPE (${scopeOptions.name}) NOT OPEN")
        }
        if (sameAsLive == true) {
            println("QWE SCOPE (${scopeOptions.name}) ALREADY OPEN, VALID, SKIPPING")
        }
        if (sameAsLive == null || sameAsLive == false) {
            initializeScope(scopeOptions)
            if (scopeOptions != containerScopeOptions && scopeOptions.storeInParent) {
                containerScopeOptions.appendDescendant(scopeOptions)
            }
        }

        scopeOptions.child?.let(::ensureScopes)
    }

    private fun isSameAsLive(scopeOptions: ScopeOptions): Boolean? {
        if (!Toothpick.isScopeOpen(scopeOptions.name)) return null

        val liveScopeOptions: ScopeOptions = Toothpick.openScope(scopeOptions.name).getInstance()
        println("QWE IS SAME (${scopeOptions.name}) ${scopeOptions.instanceId} LIVE ${liveScopeOptions.instanceId}")
        return scopeOptions.instanceId == liveScopeOptions.instanceId
    }

    private fun initializeScope(scopeOptions: ScopeOptions): Scope {
        return Toothpick.openScopes(scopeOptions.parentName, scopeOptions.name)
            .installModules(*scopeOptions.scopeArguments.createModules(), ScopeOptionsModule(scopeOptions))
            .also {
                println("QWE SCOPE NEWLY OPENED ${scopeOptions.instanceId} (${scopeOptions.name}) parent ($scopeOptions.parentName)")
            }
    }

    override fun toString(): String {
        val scopeChainString = StringBuilder(containerScopeOptions.name.toString())
        var descendant = containerScopeOptions.child
        while (descendant != null) {
            scopeChainString.append(descendant.name, ", ")
            descendant = descendant.child
        }
        return "InjectorFragmentLifecycleCallbacks (scopes=[$scopeChainString])"
    }

    private fun println(message: String) {
        if (!log) return
        kotlin.io.println(message)
    }
}
