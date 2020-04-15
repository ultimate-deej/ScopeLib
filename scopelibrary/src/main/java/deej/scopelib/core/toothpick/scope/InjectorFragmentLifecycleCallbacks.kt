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
        if (f !is UsesScope) return

        // We might have modified containerScopeOptions chain before adding the fragment `f`.
        // Make sure all preconditions are satisfied.
        ensureScopes(containerScopeOptions)

        if (f is OpensScope) {
            val fragmentScopeOptions = f.scopeOptions
            if (fragmentScopeOptions.storeInPrevious) {
                containerScopeOptions.appendTail(fragmentScopeOptions)
            }
            ensureScopes(fragmentScopeOptions)
        }

        check(Toothpick.isScopeOpen(f.usedScopeName))

        Toothpick.openScope(f.usedScopeName)
            .inject(f)
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is OpensScope) return
        if (f.isStateSaved) return

        val scopeOptionsInFragment = f.scopeOptions

        if (isSameAsLive(scopeOptionsInFragment)) {
            log("CLOSING SCOPE ${scopeOptionsInFragment.name}")
            Toothpick.closeScope(scopeOptionsInFragment.name)
            containerScopeOptions.removeStartingFrom(scopeOptionsInFragment.name, scopeOptionsInFragment.instanceId)
        }
    }

    /**
     * Ensures that scope/scope chain described by [scopeOptions] is open and matches the requirements of [scopeOptions].
     */
    private fun ensureScopes(scopeOptions: ScopeOptions) {
        if (!isSameAsLive(scopeOptions)) {
            Toothpick.closeScope(scopeOptions.name)
            initializeScope(scopeOptions)
        }

        scopeOptions.next?.let(::ensureScopes)
    }

    /**
     * Checks scope state.
     *
     * @param scopeOptions
     *  Describes what we expect the open scope to be.
     *
     * @return
     *  true if the scope is open and matches [scopeOptions].
     *
     *  false if the scope either:
     *   1. isn't open at the moment
     *   2. open but doesn't match [scopeOptions]. Needs to be reopened with new arguments.
     */
    private fun isSameAsLive(scopeOptions: ScopeOptions): Boolean {
        if (!Toothpick.isScopeOpen(scopeOptions.name)) return false

        try {
            val liveScopeOptions: ScopeOptions = Toothpick.openScope(scopeOptions.name).getInstance()
            return scopeOptions.instanceId == liveScopeOptions.instanceId
        } catch (e: Throwable) {
            throw e
        }
    }

    /**
     * Opens a scope as described by [scopeOptions].
     */
    private fun initializeScope(scopeOptions: ScopeOptions): Scope {
        return Toothpick.openScopes(scopeOptions.parentName, scopeOptions.name)
            .installModules(*scopeOptions.scopeArguments.createModules(), ScopeOptionsModule(scopeOptions))
            .also {
                log("OPENING SCOPE ${scopeOptions.parentName} -> ${scopeOptions.name} ${c++}")
            }
    }

    override fun toString(): String {
        val scopeChainString = StringBuilder(containerScopeOptions.name.toString())
        var descendant = containerScopeOptions.next
        while (descendant != null) {
            scopeChainString.append(", ", descendant.name)
            descendant = descendant.next
        }
        return "InjectorFragmentLifecycleCallbacks (scopes=[$scopeChainString])"
    }

    private fun log(message: String) {
        if (!log) return
        println("QWE $message")
    }
}

var c = 0
