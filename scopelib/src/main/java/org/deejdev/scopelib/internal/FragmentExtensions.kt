package org.deejdev.scopelib.internal

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import org.deejdev.scopelib.ScopeOptions

internal var Fragment.usedScopeName: Any?
    get() {
        val scopeName = arguments?.getParcelable(ARGUMENT_USED_SCOPE_NAME) as ScopeName?
        return scopeName?.value
    }
    set(value) {
        value?.let(ScopeNameParceler::checkSupported)

        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().putParcelable(ARGUMENT_USED_SCOPE_NAME, value?.let(::ScopeName))
    }

internal var Fragment.scopeOptions: ScopeOptions?
    get() = arguments?.getParcelable(ARGUMENT_SCOPE_OPTIONS)
    set(value) {
        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().putParcelable(ARGUMENT_SCOPE_OPTIONS, value)
    }

internal val Fragment.isDropping: Boolean
    get() = !isStateSaved || (parentFragment?.isDropping == true) // If any of the ancestors is being dropped, we are too

@Parcelize
private class ScopeName(val value: @WriteWith<ScopeNameParceler> Any) : Parcelable

private const val ARGUMENT_SCOPE_OPTIONS = "org.deejdev.scopelib.ARGUMENT_SCOPE_OPTIONS"
private const val ARGUMENT_USED_SCOPE_NAME = "org.deejdev.scopelib.ARGUMENT_USED_SCOPE_NAME"
