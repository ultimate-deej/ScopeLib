package deej.scopelib

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import toothpick.Scope
import toothpick.Toothpick
import javax.inject.Inject

class InjectorFragmentLifecycleCallbacks @Inject constructor(
    private val parentScope: Scope
) : FragmentManager.FragmentLifecycleCallbacks() {
    private val scopeController = AndroidToothpickScopeController(parentScope)
//    private val scopeOpener = ScopeOpener<Parcelable, ScopeArguments>(scopeController)

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        if (f is OpensScope) {
//            scopeOpener.ensureOpen(f.scopeArguments)
            Toothpick.openScope(f.scopeArguments.name).inject(f)
        } else {
            parentScope.inject(f)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f is OpensScope && !f.isStateSaved) {
            scopeController.close(f.scopeArguments)
        }
    }

    override fun toString() = "InjectorFragmentLifecycleCallbacks (scope=${parentScope.name})"
}
