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
    val instanceId: String = UUID.randomUUID().toString(),
    var next: ScopeOptions? = null
) : Parcelable {
    val tail: ScopeOptions
        get() = next?.tail ?: this

    fun appendTail(newTail: ScopeOptions) {
        check(newTail.managedByParent) { "managedByParent is false in $newTail" }
        if (newTail.name == name) {
            check(newTail.instanceId == instanceId) { "Cycle detected while trying to append $newTail" }
            return
        }
        val next = this.next
        if (next != null) {
            next.appendTail(newTail)
        } else {
            this.next = newTail
        }
    }

    fun removeStartingFrom(name: Any, instanceId: String?) {
        var node: ScopeOptions? = this
        while (node != null) {
            // If instanceId is specified, it must match. If not, only check name.
            val next = node.next
            if (next?.name == name) {
                if (instanceId == null || next.instanceId == instanceId) {
                    check(next.managedByParent)
                    node.next = null
                }
                return
            }

            node = next
        }
    }

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
