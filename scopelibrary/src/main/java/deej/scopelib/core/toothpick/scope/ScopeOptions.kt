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
    val storeInPrevious: Boolean = false,
    val instanceId: String = UUID.randomUUID().toString(),
    var next: ScopeOptions? = null
) : Parcelable {
    val tail: ScopeOptions
        get() = next?.tail ?: this

    fun appendTail(newTail: ScopeOptions) {
        check(newTail.storeInPrevious) { "storeInPrevious is false in $newTail" }
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
                    check(next.storeInPrevious)
                    node.next = null
                }
                return
            }

            node = next
        }
    }
}
