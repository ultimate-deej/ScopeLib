package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

class InjectorFragmentLifecycleCallbacks @Inject constructor(
    private val parentScope: Scope
) : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f is OpensScope) {
            Toothpick.openScope(f.scopeArguments.name)
                .installModules(*f.scopeArguments.createModules())
                .inject(f)
        } else {
            parentScope.inject(f)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f is OpensScope && !f.isStateSaved) {
            Toothpick.closeScope(f.scopeArguments.name)
        }
    }

    override fun toString() = "InjectorFragmentLifecycleCallbacks (scope=${parentScope.name})"
}
