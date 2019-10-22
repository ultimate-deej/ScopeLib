package deej.scopelib.core.toothpick.scope

import android.os.Parcelable
import toothpick.config.Module

interface AndroidToothpickScopeArguments : ScopeArguments, Parcelable {
    fun createModules(): Array<Module> = emptyArray()
}
