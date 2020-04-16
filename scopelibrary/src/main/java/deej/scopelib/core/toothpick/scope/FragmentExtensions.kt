package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment

// TODO: internal
var Fragment.scopeOptions: ScopeOptions?
    get() = arguments?.getParcelable(ARGUMENT_SCOPE_OPTIONS)
    set(value) {
        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().putParcelable(ARGUMENT_SCOPE_OPTIONS, value)
    }

// TODO: full package name?
private const val ARGUMENT_SCOPE_OPTIONS = "deej.scopelib.scopeOptions"
