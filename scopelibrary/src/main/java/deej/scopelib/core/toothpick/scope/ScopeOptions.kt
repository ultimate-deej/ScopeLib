package deej.scopelib.core.toothpick.scope

import android.os.Debug
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class ScopeOptions(
    val name: @RawValue Any,
    val scopeArguments: ScopeArguments,
    val parentName: @RawValue Any?,
    val storeInParent: Boolean = false,
    val instanceId: String = UUID.randomUUID().toString(),
    var child: ScopeOptions? = null
) : Parcelable {
    val tail: ScopeOptions
        get() = child?.tail ?: this

    fun appendDescendant(descendant: ScopeOptions) {
        check(descendant.storeInParent)
        check(descendant != this) {
            Debug.waitForDebugger()
            "Cycle detected while trying to append $descendant"
        }
        val child = this.child
        if (child != null) {
            child.appendDescendant(descendant)
        } else {
            this.child = descendant
        }
    }

    fun removeDescendant(name: Any, instanceId: String?) {
        var node: ScopeOptions? = this
        while (node != null) {
            // If instanceId is specified, it must match. If not, only check name.
            val descendant = node.child
            if (descendant?.name == name) {
                if (instanceId == null || descendant.instanceId == instanceId) {
                    check(descendant.storeInParent)
                    node.child = null
                }
                return
            }

            node = descendant
        }
    }
}
