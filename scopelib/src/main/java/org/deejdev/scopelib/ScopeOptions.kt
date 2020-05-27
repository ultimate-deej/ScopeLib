package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import org.deejdev.scopelib.internal.ScopeNameParceler
import org.deejdev.scopelib.internal.formatScopeName
import java.util.*

@Parcelize
data class ScopeOptions internal constructor(
    val name: @WriteWith<ScopeNameParceler> Any,
    val scopeArguments: ScopeArguments,
    val parentName: @WriteWith<ScopeNameParceler> Any,
    internal val instanceId: String
) : Parcelable {
    constructor(name: Any, scopeArguments: ScopeArguments, parentName: Any) : this(name, scopeArguments, parentName, UUID.randomUUID().toString())

    init {
        ScopeNameParceler.checkSupported(name)
        ScopeNameParceler.checkSupported(parentName)
    }

    override fun toString() = "${formatScopeName(name)}($scopeArguments)[${instanceId.take(8)}]{parent=${formatScopeName(parentName)}}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScopeOptions

        if (name != other.name) return false
        if (instanceId != other.instanceId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + instanceId.hashCode()
        return result
    }
}
