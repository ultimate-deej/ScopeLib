package org.deejdev.scopelib

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import org.deejdev.scopelib.internal.*
import toothpick.ktp.KTP

class ScopeOptionsManagerCallbacks(
    private val scopeOptionsManager: ScopeOptionsManager
) : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentPreCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        check(f.scopeOptions == null || f.scopeOptions?.instanceId == f.uniqueInstanceId) {
            "`ScopeOptions.instanceId` is not in sync with `Fragment.uniqueInstanceId`"
        }
        // If the fragment declares a scope to open, make sure the record is stored in the manager
        f.scopeOptions?.let(scopeOptionsManager::overwrite)
        // If the fragment specifies a scope name to use, inject
        f.usedScopeName?.let { usedScopeName ->
            scopeOptionsManager.materialize()
            check(KTP.isScopeOpen(usedScopeName)) { "Required scope `${formatScopeName(usedScopeName)}` is not open" }
            KTP.openScope(usedScopeName)
                .inject(f)
        }
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        if (f.isDropping) {
            // The fragment is not going to be restored, close the scope it declares
            f.scopeOptions?.let(scopeOptionsManager::removeAndClose)
        }
    }
}
