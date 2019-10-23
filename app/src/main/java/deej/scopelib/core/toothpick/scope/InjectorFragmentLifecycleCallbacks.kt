package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

class TTT : OpensScopeFragment

class InjectorFragmentLifecycleCallbacks @Inject constructor(
    private val parentScope: Scope
) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f !is OpensScope) {
            parentScope.inject(f)
            return
        }

//        TTT().scopeOptions

        val scopeOptionsInFragment = f.scopeOptions

        if (Toothpick.isScopeOpen(scopeOptionsInFragment.name)) {
            val liveScope = Toothpick.openScope(scopeOptionsInFragment.name)
            val liveScopeOptions = liveScope.getInstance(ScopeOptions::class.java)
            println("QWE LIVE ID ${liveScopeOptions.instanceId}, OPENING ID ${scopeOptionsInFragment.instanceId}")
            if (liveScopeOptions.instanceId == scopeOptionsInFragment.instanceId) {
                liveScope.inject(f)
                println("QWE SCOPE ALREADY OPEN, VALID, SKIPPING")
                return
            } else {
                Toothpick.closeScope(scopeOptionsInFragment.name)
                println("QWE SCOPE ALREADY OPEN, INVALID, CLOSING")
                // NOTE: Do not return yet
            }
        }

        Toothpick.openScopes(parentScope.name, scopeOptionsInFragment.name)
            .installModules(*scopeOptionsInFragment.scopeArguments.createModules(), ScopeOptionsModule(scopeOptionsInFragment))
            .inject(f)
        println("QWE SCOPE NEWLY OPENED ${scopeOptionsInFragment.instanceId}")
    }

    private fun <T> someStuff(fragment: Fragment) where T : Fragment, T : OpensScope {
        @Suppress("UNCHECKED_CAST")
        fragment as T
        fragment.scopeOptions
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is OpensScope) return

        val scopeOptionsInFragment = f.scopeOptions
        if (!f.isStateSaved) {
            val liveScopeOptions = Toothpick.openScope(scopeOptionsInFragment.name).getInstance(ScopeOptions::class.java)
            println("QWE LIVE ID ${liveScopeOptions.instanceId}, DESTROYING ID ${scopeOptionsInFragment.instanceId}")
            if (liveScopeOptions.instanceId == scopeOptionsInFragment.instanceId) {
                Toothpick.closeScope(scopeOptionsInFragment.name)
                println("QWE SCOPE CLOSED ON DESTROY")
            } else {
                println("QWE SCOPE KEPT ON DESTROY")
            }
        }
    }

    // TODO: Print the whole chain
    override fun toString() = "InjectorFragmentLifecycleCallbacks (scope=${parentScope.name})"
}
