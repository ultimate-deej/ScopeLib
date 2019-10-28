package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject
import kotlin.reflect.KMutableProperty0

class InjectorFragmentLifecycleCallbacks @Inject constructor(
    private val currentScopeOptionsReference: KMutableProperty0<ScopeOptions>
) : FragmentManager.FragmentLifecycleCallbacks() {

    private var currentScopeOptions: ScopeOptions
        get() = currentScopeOptionsReference.get()
        set(value) = currentScopeOptionsReference.set(value)

    init {
        restoreIfNeeded(currentScopeOptions)
        println("QWE RESTORED ALL $this")
    }

    private fun restoreIfNeeded(scopeOptions: ScopeOptions) {
        // The first scope in the chain (the one without parent) is always initialized before getting into InjectorFragmentLifecycleCallbacks.
        // So skip its initialization.
        val parent = scopeOptions.parent ?: return
        // If the scope is present its parents are also there. No need to check for them.
        if (Toothpick.isScopeOpen(scopeOptions.name)) return

        restoreIfNeeded(parent)
        initializeScope(scopeOptions)
        println("QWE SCOPE RESTORED (${scopeOptions.name})")
    }

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f !is OpensScope) {
            Toothpick.openScope(currentScopeOptions.name)
                .inject(f)
            return
        }

        val scopeOptionsInFragment = f.scopeOptions

        if (scopeOptionsInFragment.name == currentScopeOptions.name) {
            reopenCurrentScope(scopeOptionsInFragment)
                .inject(f)
        } else {
            openChildScope(scopeOptionsInFragment)
                .inject(f)
        }
    }

    private fun reopenCurrentScope(scopeOptionsInFragment: ScopeOptions): Scope {
        return if (currentScopeOptions.instanceId == scopeOptionsInFragment.instanceId) {
            // Android simply recreated the fragment, the existing scope is valid.
            println("QWE SCOPE ALREADY OPEN, VALID, SKIPPING")
            Toothpick.openScope(scopeOptionsInFragment.name)
        } else {
            // instanceId is different which means the fragment was recreated by the programmer. We can't be sure if we can reuse the existing scope, so just recreate it.
            println("QWE SCOPE ALREADY OPEN, INVALID, CLOSING ${currentScopeOptions.instanceId}")
            closeCurrentScope()
            scopeOptionsInFragment.parent = currentScopeOptions
            currentScopeOptions = scopeOptionsInFragment
            initializeScope(scopeOptionsInFragment)
        }
    }

    private fun openChildScope(scopeOptionsInFragment: ScopeOptions): Scope {
        // Don't allow to open a scope if it's already opened somewhere else in the scope tree.
        check(!Toothpick.isScopeOpen(scopeOptionsInFragment.name)) {
            "${scopeOptionsInFragment.name} is already open."
        }

        if (scopeOptionsInFragment.extends) {
            scopeOptionsInFragment.parent = currentScopeOptions
            currentScopeOptions = scopeOptionsInFragment
            println("QWE REQUESTED SCOPE IS NEW, EXTENDING")
        } else {
            println("QWE REQUESTED SCOPE IS NEW, NOT EXTENDING")
        }
        // If extends == false then assume that the new scope is managed by the new fragment.
        // But keep using the old one here.

        return initializeScope(scopeOptionsInFragment)
    }

    private fun initializeScope(scopeOptions: ScopeOptions): Scope {
        return Toothpick.openScopes(scopeOptions.parent!!.name, scopeOptions.name)
            .installModules(*scopeOptions.scopeArguments.createModules(), ScopeOptionsModule(scopeOptions))
            .also {
                println("QWE SCOPE NEWLY OPENED ${scopeOptions.instanceId} (${scopeOptions.name})")
            }
    }

    private fun closeCurrentScope() {
        Toothpick.closeScope(currentScopeOptions.name)
        val parentScopeOptions = currentScopeOptions.parent
        if (parentScopeOptions != null) {
            currentScopeOptions = parentScopeOptions
        }
        // parent is null means the container is closing for good, and we'll not come back in this instance.
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is OpensScope) return

        val scopeOptionsInFragment = f.scopeOptions
        if (!f.isStateSaved) {
            println("QWE DESTROY REQUESTED FOR ${scopeOptionsInFragment.instanceId}")
            if (currentScopeOptions.instanceId == scopeOptionsInFragment.instanceId) {
                closeCurrentScope()
                println("QWE LIVE ${currentScopeOptions.instanceId}, CLOSING")
            } else {
                println("QWE LIVE ${currentScopeOptions.instanceId}, KEEPING")
            }
        }
    }

    override fun toString(): String {
        val scopeChainString = StringBuilder(currentScopeOptions.name.toString())
        var ancestor = currentScopeOptions.parent
        while (ancestor != null) {
            scopeChainString.insert(0, "${ancestor.name}, ")
            ancestor = ancestor.parent
        }
        return "InjectorFragmentLifecycleCallbacks (scopes=[$scopeChainString])"
    }
}
