package deej.thoroughtestapp.presentation.scopearguments

import deej.thoroughtestapp.core.toothpick.modules.TabsModule
import kotlinx.android.parcel.Parcelize
import org.deejdev.scopelib.ScopeArguments
import toothpick.config.Module

@Parcelize
data class TabsScopeArguments(
    val param: String
) : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        TabsModule(param)
    )
}
