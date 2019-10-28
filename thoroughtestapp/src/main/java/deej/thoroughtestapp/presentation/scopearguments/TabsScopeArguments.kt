package deej.thoroughtestapp.presentation.scopearguments

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.thoroughtestapp.core.toothpick.modules.TabsModule
import kotlinx.android.parcel.Parcelize
import toothpick.config.Module

@Parcelize
data class TabsScopeArguments(
    val param: String
) : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        TabsModule(param)
    )
}
