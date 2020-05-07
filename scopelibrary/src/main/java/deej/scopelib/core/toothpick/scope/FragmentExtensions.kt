package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.attachScopeOptions(scopeOptions: ScopeOptions, alsoUse: Boolean = true) {
    if (arguments == null) {
        arguments = Bundle()
    }
    requireArguments().putParcelable(ARGUMENT_SCOPE_OPTIONS, scopeOptions)
    if (alsoUse) {
        usedScopeName = scopeOptions.name
    }
}

fun Fragment.useScope(name: Class<out Annotation>?) {
    usedScopeName = name
}

internal var Fragment.usedScopeName: Class<out Annotation>?
    @Suppress("UNCHECKED_CAST")
    get() = arguments?.getString(ARGUMENT_USED_SCOPE_NAME)?.let { Class.forName(it) } as Class<out Annotation>?
    private set(value) {
        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().putString(ARGUMENT_USED_SCOPE_NAME, value?.name)
    }

internal val Fragment.scopeOptions: ScopeOptions?
    get() = arguments?.getParcelable(ARGUMENT_SCOPE_OPTIONS)

internal val Fragment.isDropping: Boolean
    get() = when (val parent = parentFragment) {
        null -> !isStateSaved && isRemoving
        else -> parent.isDropping // If any of the ancestors is being dropped, we are too
    }

// TODO: full package name?
private const val ARGUMENT_SCOPE_OPTIONS = "deej.scopelib.scopeOptions"
private const val ARGUMENT_USED_SCOPE_NAME = "deej.scopelib.usedScopeName"
