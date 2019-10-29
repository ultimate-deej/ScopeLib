package deej.thoroughtestapp.presentation.scopearguments

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.thoroughtestapp.core.toothpick.modules.SimpleScopedTabModule
import kotlinx.android.parcel.Parcelize
import toothpick.config.Module

@Parcelize
object SimpleTabScopeArguments : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        SimpleScopedTabModule()
    )
}
