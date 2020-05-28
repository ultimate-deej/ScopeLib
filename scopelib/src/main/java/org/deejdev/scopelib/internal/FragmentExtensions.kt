package org.deejdev.scopelib.internal

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.deejdev.scopelib.ScopeOptions

internal var Fragment.usedScopeName: Any?
    get() = arguments?.usedScopeName
    set(value) {
        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().usedScopeName = value
    }

internal var Fragment.scopeOptions: ScopeOptions?
    get() = arguments?.scopeOptions
    set(value) {
        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().scopeOptions = value
    }

internal val Fragment.uniqueInstanceId: String?
    get() = arguments?.uniqueInstanceId

internal fun Fragment.ensureUniqueInstanceId(): String {
    if (arguments == null) {
        arguments = Bundle()
    }
    return requireArguments().ensureUniqueInstanceId()
}

internal val Fragment.isDropping: Boolean
    get() = !isStateSaved || (parentFragment?.isDropping == true) // If any of the ancestors is being dropped, we are too
