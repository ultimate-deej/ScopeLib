package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import org.deejdev.scopelib.internal.ScopeNameParceler
import org.deejdev.scopelib.internal.formatScopeName
import kotlin.reflect.KClass

@Parcelize
data class ScopeOptions internal constructor(
    val name: @WriteWith<ScopeNameParceler> Any,
    val scopeArguments: ScopeArguments,
    val parentName: @WriteWith<ScopeNameParceler> Any,
    internal var instanceId: String
) : Parcelable {
    constructor(name: Any, scopeArguments: ScopeArguments, parentName: Any) : this(javaifyClass(name), scopeArguments, javaifyClass(parentName), ID_UNINITIALIZED)

    init {
        ScopeNameParceler.checkSupported(name)
        ScopeNameParceler.checkSupported(parentName)
    }

    val isAttached: Boolean
        get() = instanceId != ID_UNINITIALIZED

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

private const val ID_UNINITIALIZED = "UNINITIALIZED"

private fun javaifyClass(name: Any): Any = when (name) {
    is KClass<*> -> name.java
    else -> name
}
