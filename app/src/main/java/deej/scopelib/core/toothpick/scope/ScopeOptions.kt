package deej.scopelib.core.toothpick.scope

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class ScopeOptions(
    val name: @RawValue Any,
    val scopeArguments: ScopeArguments,
    val instanceId: String = UUID.randomUUID().toString()
) : Parcelable
