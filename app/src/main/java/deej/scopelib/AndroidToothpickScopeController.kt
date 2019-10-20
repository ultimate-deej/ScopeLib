package deej.scopelib

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import toothpick.Scope
import toothpick.Toothpick

class AndroidToothpickScopeController(private val parentScope: Scope) : ScopeController<AndroidToothpickScopeArguments> {
//    private var currentEntry = Entry()

    override fun open(arguments: AndroidToothpickScopeArguments) {
        Toothpick.openScopes(parentScope.name, arguments.name).apply {
            installModules(*arguments.createModules(), AndroidToothpickScopeArgumentsModule(arguments))
        }
    }

    override fun close(name: Any) {
        Toothpick.closeScope(name)
    }

    override fun retrieveArguments(name: Any): AndroidToothpickScopeArguments? = when {
        Toothpick.isScopeOpen(name) -> Toothpick.openScope(name).getInstance(AndroidToothpickScopeArguments::class.java)
        else -> null
    }

    @Parcelize
    private class Entry(val arguments: AndroidToothpickScopeArguments, val parent: Entry? = null) : Parcelable
}


@Parcelize
data class TestArgs(override val name: @RawValue Any) : AndroidToothpickScopeArguments
