package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.deejdev.scopelib.internal.ScopeNameParceler
import org.deejdev.scopelib.internal.formatScopeName

@Parcelize
data class ScopeBlueprint internal constructor(
    val name: @WriteWith<ScopeNameParceler> Any,
    val modulesFactory: ScopeModulesFactory,
    val parentName: @WriteWith<ScopeNameParceler> Any,
    internal var instanceId: String
) : Parcelable {
    constructor(name: Any, modulesFactory: ScopeModulesFactory, parentName: Any) : this(name, modulesFactory, parentName, ID_UNATTACHED)

    init {
        ScopeNameParceler.checkSupported(name)
        ScopeNameParceler.checkSupported(parentName)
    }

    val isAttached: Boolean
        get() = instanceId != ID_UNATTACHED

    override fun toString() = "${formatScopeName(name)}($modulesFactory)[${instanceId.take(8)}]{parent=${formatScopeName(parentName)}}"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScopeBlueprint

        if (name != other.name) return false
        if (instanceId != other.instanceId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + instanceId.hashCode()
        return result
    }

    companion object {
        inline operator fun <reified Name : Annotation, reified ParentName : Annotation> invoke(modulesFactory: ScopeModulesFactory) =
            ScopeBlueprint(Name::class.java, modulesFactory, ParentName::class.java)
    }
}

private const val ID_UNATTACHED = "UNATTACHED"
