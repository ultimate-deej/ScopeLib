package deej.scopelib.core.toothpick.scope

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class ScopeOptions(
    val name: @RawValue Any,
    val scopeArguments: ScopeArguments,
    val extends: Boolean = false,
    val instanceId: String = UUID.randomUUID().toString(),
    var extension: ScopeOptions? = null
) : Parcelable {
    val head: ScopeOptions
        get() = extension?.head ?: this

    fun extend(extension: ScopeOptions) {
        check(extension.extends)
        head.extension = extension
    }

    fun removeExtension(name: Any, instanceId: String?) {
        var node: ScopeOptions? = this
        while (node != null) {
            // If instanceId is specified, it must match. If not, only check name.
            val extension = node.extension
            if (extension?.name == name) {
                if (instanceId == null || extension.instanceId == instanceId) {
                    check(extension.extends)
                    node.extension = null
                }
                return
            }

            node = extension
        }
    }
}
