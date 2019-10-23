package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import deej.scopelib.BaseFragment
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

class InjectorFragmentLifecycleCallbacks @Inject constructor(
    private val parentScope: Scope
) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f !is BaseFragment) {
            parentScope.inject(f)
            return
        }

        val scopeArgumentsInFragment = f.scopeArguments

        if (scopeArgumentsInFragment == null) {
            parentScope.inject(f)
            return
        }

        if (Toothpick.isScopeOpen(scopeArgumentsInFragment.name)) {
            val liveScope = Toothpick.openScope(scopeArgumentsInFragment.name)
            val liveScopeArguments = liveScope.getInstance(AndroidToothpickScopeArguments::class.java)
            println("QWE LIVE ID ${liveScopeArguments.instanceId}, OPENING ID ${scopeArgumentsInFragment.instanceId}")
            if (liveScopeArguments.instanceId == scopeArgumentsInFragment.instanceId) {
                liveScope.inject(f)
                println("QWE SCOPE ALREADY OPEN, VALID, SKIPPING")
                return
            } else {
                Toothpick.closeScope(scopeArgumentsInFragment.name)
                println("QWE SCOPE ALREADY OPEN, INVALID, CLOSING")
                // NOTE: Do not return yet
            }
        }

        Toothpick.openScopes(parentScope.name, scopeArgumentsInFragment.name)
            .installModules(*scopeArgumentsInFragment.createModules(), AndroidToothpickScopeArgumentsModule(scopeArgumentsInFragment))
            .inject(f)
        println("QWE SCOPE NEWLY OPENED ${scopeArgumentsInFragment.instanceId}")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f !is BaseFragment) return

        val scopeArgumentsInFragment = f.scopeArguments
        if (scopeArgumentsInFragment != null && !f.isStateSaved) {
            val liveScopeArguments = Toothpick.openScope(scopeArgumentsInFragment.name).getInstance(AndroidToothpickScopeArguments::class.java)
            println("QWE LIVE ID ${liveScopeArguments.instanceId}, DESTROYING ID ${scopeArgumentsInFragment.instanceId}")
            if (liveScopeArguments.instanceId == scopeArgumentsInFragment.instanceId) {
                Toothpick.closeScope(scopeArgumentsInFragment.name)
                println("QWE SCOPE CLOSED ON DESTROY")
            } else {
                println("QWE SCOPE KEPT ON DESTROY")
            }
        }
    }

    override fun toString() = "InjectorFragmentLifecycleCallbacks (scope=${parentScope.name})"
}
