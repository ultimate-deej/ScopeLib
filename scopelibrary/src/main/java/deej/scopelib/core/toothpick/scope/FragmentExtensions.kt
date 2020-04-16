package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment

// TODO: internal
fun Fragment.attachScopeOptions(scopeOptions: ScopeOptions, alsoUse: Boolean = true) {
    if (arguments == null) {
        arguments = Bundle()
    }
    requireArguments().putParcelable(ARGUMENT_SCOPE_OPTIONS, scopeOptions)
    if (alsoUse) {
        usedScopeName = scopeOptions.name
    }
}

val Fragment.scopeOptions: ScopeOptions?
    get() = arguments?.getParcelable(ARGUMENT_SCOPE_OPTIONS)

var Fragment.usedScopeName: Class<out Annotation>?
    @Suppress("UNCHECKED_CAST")
    get() = arguments?.getString(ARGUMENT_USED_SCOPE_NAME)?.let { Class.forName(it) } as Class<out Annotation>?
    set(value) {
        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().putString(ARGUMENT_USED_SCOPE_NAME, value?.name)
    }

// TODO: full package name?
private const val ARGUMENT_SCOPE_OPTIONS = "deej.scopelib.scopeOptions"
private const val ARGUMENT_USED_SCOPE_NAME = "deej.scopelib.usedScopeName"
