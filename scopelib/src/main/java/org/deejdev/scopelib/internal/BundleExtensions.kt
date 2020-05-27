package org.deejdev.scopelib.internal

import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import org.deejdev.scopelib.ScopeOptions

internal var Bundle.usedScopeName: Any?
    get() {
        val scopeName = getParcelable(KEY_USED_SCOPE_NAME) as ScopeName?
        return scopeName?.value
    }
    set(value) {
        value?.let(ScopeNameParceler::checkSupported)

        putParcelable(KEY_USED_SCOPE_NAME, value?.let(::ScopeName))
    }

internal var Bundle.scopeOptions: ScopeOptions?
    get() = getParcelable(KEY_SCOPE_OPTIONS)
    set(value) {
        putParcelable(KEY_SCOPE_OPTIONS, value)
        if (value != null) {
            uniqueInstanceId = value.instanceId
        }
    }

internal var Bundle.uniqueInstanceId: String?
    get() = getString(KEY_UNIQUE_ID)
    set(value) {
        check(value != null) { "`uniqueInstanceId` cannot be set to null." }

        uniqueInstanceId.let {
            check(it == null || it == value) { "Can't assign \"$value\" to `uniqueInstanceId`, current value is \"$it\"." }
        }

        putString(KEY_UNIQUE_ID, value)
    }

@Parcelize
private class ScopeName(val value: @WriteWith<ScopeNameParceler> Any) : Parcelable

private const val KEY_SCOPE_OPTIONS = "org.deejdev.scopelib.SCOPE_OPTIONS"
private const val KEY_USED_SCOPE_NAME = "org.deejdev.scopelib.USED_SCOPE_NAME"
private const val KEY_UNIQUE_ID = "org.deejdev.scopelib.UNIQUE_ID"
