package deej.scopelib.core.toothpick.scope

import android.os.Parcel
import kotlinx.android.parcel.Parceler

object ScopeAnnotationParceler : Parceler<Class<out Annotation>?> {
    @Suppress("UNCHECKED_CAST")
    override fun create(parcel: Parcel) = parcel.readString()?.let { Class.forName(it) } as Class<out Annotation>?

    override fun Class<out Annotation>?.write(parcel: Parcel, flags: Int) = parcel.writeString(this?.name)
}
