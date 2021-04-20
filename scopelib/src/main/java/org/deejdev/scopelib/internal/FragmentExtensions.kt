package org.deejdev.scopelib.internal

import android.os.Bundle
import androidx.fragment.app.Fragment

internal val Fragment.usedScopeName: Any?
    get() = arguments?.usedScopeName

internal val Fragment.uniqueInstanceId: String?
    get() = arguments?.uniqueInstanceId

internal fun Fragment.ensureUniqueInstanceId(): String = ensureArguments().ensureUniqueInstanceId()

internal val Fragment.isDropping: Boolean
    get() = (activity?.isFinishing == true) || isFragmentChainDropping

private val Fragment.isFragmentChainDropping: Boolean
    get() = !isStateSaved || (parentFragment?.isDropping == true) // If any of the ancestors is being dropped, we are too

internal fun Fragment.ensureArguments(): Bundle {
    if (arguments == null) {
        arguments = Bundle()
    }
    return requireArguments()
}
