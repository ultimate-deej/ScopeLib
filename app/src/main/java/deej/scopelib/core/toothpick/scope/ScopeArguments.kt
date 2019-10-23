package deej.scopelib.core.toothpick.scope

import android.os.Parcelable
import toothpick.config.Module

abstract class ScopeArguments : Parcelable {
    open fun createModules(): Array<Module> = emptyArray()
}
