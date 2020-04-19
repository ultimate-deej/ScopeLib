package deej.scopelib.core.toothpick.scope

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.WriteWith
import java.util.*

@Parcelize
data class ScopeOptions(
    val name: @WriteWith<ScopeAnnotationParceler> Class<out Annotation>,
    val scopeArguments: ScopeArguments,
    val parentName: @WriteWith<ScopeAnnotationParceler> Class<out Annotation>?,
    val managedByParent: Boolean = false,
    val instanceId: String = UUID.randomUUID().toString()
) : Parcelable {
    override fun toString() = "${name.simpleName}($scopeArguments)[${instanceId.take(8)}]{parent=${parentName?.simpleName}}"

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
