package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import deej.scopelib.presentation.screens.externalScopeArguments
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

class InjectorFragmentLifecycleCallbacks @Inject constructor(
    private val parentScope: Scope
) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        val scopeArguments = f.externalScopeArguments
        if (scopeArguments != null) {
            if (Toothpick.isScopeOpen(scopeArguments.name)) {
                Toothpick.openScope(scopeArguments.name)
                    .inject(f)
                println("SCOPE ALREADY OPEN")
            } else {
                Toothpick.openScopes(parentScope.name, scopeArguments.name)
                    .installModules(*scopeArguments.createModules())
                    .inject(f)
                println("SCOPE OPENED")
            }
        } else {
            parentScope.inject(f)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        val scopeArguments = f.externalScopeArguments
        if (scopeArguments != null && !f.isStateSaved) {
            Toothpick.closeScope(scopeArguments.name)
            println("SCOPE CLOSED")
        }
    }

    override fun toString() = "InjectorFragmentLifecycleCallbacks (scope=${parentScope.name})"
}
