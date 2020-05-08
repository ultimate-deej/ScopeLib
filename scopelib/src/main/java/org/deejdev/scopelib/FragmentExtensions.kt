package org.deejdev.scopelib

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith

fun Fragment.attachScopeOptions(scopeOptions: ScopeOptions, alsoUse: Boolean = true) {
    if (arguments == null) {
        arguments = Bundle()
    }
    requireArguments().putParcelable(ARGUMENT_SCOPE_OPTIONS, scopeOptions)
    if (alsoUse) {
        usedScopeName = scopeOptions.name
    }
}

fun Fragment.useScope(name: Any?) {
    usedScopeName = name
}

internal var Fragment.usedScopeName: Any?
    get() {
        val scopeName = arguments?.getParcelable(ARGUMENT_USED_SCOPE_NAME) as ScopeName?
        return scopeName?.value
    }
    private set(value) {
        value?.let(ScopeNameParceler::checkSupported)

        if (arguments == null) {
            arguments = Bundle()
        }
        requireArguments().putParcelable(ARGUMENT_USED_SCOPE_NAME, value?.let(::ScopeName))
    }

internal val Fragment.scopeOptions: ScopeOptions?
    get() = arguments?.getParcelable(ARGUMENT_SCOPE_OPTIONS)

internal val Fragment.isDropping: Boolean
    get() = !isStateSaved || (parentFragment?.isDropping == true) // If any of the ancestors is being dropped, we are too

@Parcelize
private class ScopeName(val value: @WriteWith<ScopeNameParceler> Any) : Parcelable

private const val ARGUMENT_SCOPE_OPTIONS = "org.deejdev.scopelib.ARGUMENT_SCOPE_OPTIONS"
private const val ARGUMENT_USED_SCOPE_NAME = "org.deejdev.scopelib.ARGUMENT_USED_SCOPE_NAME"
