package org.deejdev.scopelib.internal

import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import org.deejdev.scopelib.ScopeOptions

internal var Bundle.usedScopeName: Any?
    get() {
        val scopeName = getParcelable(ARGUMENT_USED_SCOPE_NAME) as ScopeName?
        return scopeName?.value
    }
    set(value) {
        value?.let(ScopeNameParceler::checkSupported)

        putParcelable(ARGUMENT_USED_SCOPE_NAME, value?.let(::ScopeName))
    }

internal var Bundle.scopeOptions: ScopeOptions?
    get() = getParcelable(ARGUMENT_SCOPE_OPTIONS)
    set(value) = putParcelable(ARGUMENT_SCOPE_OPTIONS, value)

@Parcelize
private class ScopeName(val value: @WriteWith<ScopeNameParceler> Any) : Parcelable

private const val ARGUMENT_SCOPE_OPTIONS = "org.deejdev.scopelib.ARGUMENT_SCOPE_OPTIONS"
private const val ARGUMENT_USED_SCOPE_NAME = "org.deejdev.scopelib.ARGUMENT_USED_SCOPE_NAME"
