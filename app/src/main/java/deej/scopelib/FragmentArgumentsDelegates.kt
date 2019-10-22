package deej.scopelib

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// TODO: Lambdas are not needed? These values are meant to be passed from the outside
private class ArgumentsDelegate<T>(private val putT: Bundle.(String, T) -> Unit, private val defaultValue: (() -> T?)? = null) : ReadWriteProperty<Fragment, T> {
    object Unset

    private var value: Any? = Unset

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        if (value == Unset) {
            value = thisRef.arguments?.get(property.name) as? T ?: defaultValue?.invoke()
        }
        return value as T
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        ensureArguments(thisRef)
        this.value = value
        thisRef.arguments!!.putT(property.name, value)
    }

    private fun ensureArguments(fragment: Fragment) {
        if (fragment.arguments == null) {
            fragment.arguments = Bundle()
        }
    }
}

object FragmentArgumentsDelegates {
    fun <T : String?> string(defaultValue: (() -> T?)?): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putString, defaultValue)

    fun <T : CharSequence?> charSequence(defaultValue: (() -> T?)? = null): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putCharSequence, defaultValue)

    fun <T : Parcelable?> parcelable(defaultValue: (() -> T?)? = null): ReadWriteProperty<Fragment, T> =
        ArgumentsDelegate(Bundle::putParcelable, defaultValue)

    fun boolean(defaultValue: () -> Boolean): ReadWriteProperty<Fragment, Boolean> =
        ArgumentsDelegate(Bundle::putBoolean, defaultValue)

    fun int(defaultValue: () -> Int): ReadWriteProperty<Fragment, Int> =
        ArgumentsDelegate(Bundle::putInt, defaultValue)

    fun long(defaultValue: () -> Long): ReadWriteProperty<Fragment, Long> =
        ArgumentsDelegate(Bundle::putLong, defaultValue)

    fun boolean(defaultValue: Boolean = false) = boolean { defaultValue }
    fun int(defaultValue: Int = 0) = int { defaultValue }
    fun long(defaultValue: Long = 0L) = long { defaultValue }
}

@Suppress("unused")
val Fragment.args
    get() = FragmentArgumentsDelegates
