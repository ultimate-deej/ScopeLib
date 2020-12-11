package org.deejdev.scopelib.internal

import android.os.Parcel
import kotlinx.parcelize.Parceler

internal object ScopeNameParceler : Parceler<Any?> {
    override fun create(parcel: Parcel): Any? {
        return when (val kind = parcel.readInt()) {
            0 -> return null
            1 -> parcel.readString()?.let { Class.forName(it) }
            2 -> parcel.readString()
            else -> error("Error reading scope name from Parcel: unknown type $kind")
        }
    }

    override fun Any?.write(parcel: Parcel, flags: Int) {
        when {
            this == null -> {
                parcel.writeInt(0)
            }
            this is Class<*> && this.isAnnotation -> {
                parcel.writeInt(1)
                parcel.writeString(this.name)
            }
            this is String -> {
                parcel.writeInt(2)
                parcel.writeString(this)
            }
            else -> error("")
        }
    }

    fun checkSupported(name: Any) = check((name is Class<*> && name.isAnnotation) || name is String) {
        "An instance of ${name.javaClass} as a scope name is not supported."
    }
}
