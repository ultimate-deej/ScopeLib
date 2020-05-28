package org.deejdev.scopelib.internal

import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import org.deejdev.scopelib.ScopeOptions
import java.util.*

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
        val copy = value?.copy()
        putParcelable(KEY_SCOPE_OPTIONS, copy)
        if (copy != null) {
            copy.instanceId = ensureUniqueInstanceId()
        }
    }

internal var Bundle.uniqueInstanceId: String?
    get() = getString(KEY_UNIQUE_ID)
    private set(value) {
        check(value != null) { "`uniqueInstanceId` cannot be set to null." }

        putString(KEY_UNIQUE_ID, value)
    }

internal fun Bundle.ensureUniqueInstanceId(): String {
    if (uniqueInstanceId == null) {
        uniqueInstanceId = UUID.randomUUID().toString()
    }
    return uniqueInstanceId!!
}

@Parcelize
private class ScopeName(val value: @WriteWith<ScopeNameParceler> Any) : Parcelable

private const val KEY_SCOPE_OPTIONS = "org.deejdev.scopelib.SCOPE_OPTIONS"
private const val KEY_USED_SCOPE_NAME = "org.deejdev.scopelib.USED_SCOPE_NAME"
private const val KEY_UNIQUE_ID = "org.deejdev.scopelib.UNIQUE_ID"
