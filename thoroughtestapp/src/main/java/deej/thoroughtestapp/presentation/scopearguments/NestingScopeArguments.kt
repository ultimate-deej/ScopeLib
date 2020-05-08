package deej.thoroughtestapp.presentation.scopearguments

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.thoroughtestapp.core.toothpick.modules.NavigationModule
import deej.thoroughtestapp.core.toothpick.modules.NestingModule
import deej.thoroughtestapp.core.toothpick.qualifiers.NestingNavigation
import kotlinx.android.parcel.Parcelize
import toothpick.config.Module

@Parcelize
data class NestingScopeArguments(
    val level: Int
) : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        NestingModule(level),
        NavigationModule<NestingNavigation>(false)
    )
}
