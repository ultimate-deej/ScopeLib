package deej.thoroughtestapp.presentation.scopearguments

import deej.thoroughtestapp.core.toothpick.modules.SimpleScopedTabModule
import kotlinx.android.parcel.Parcelize
import org.deejdev.scopelib.ScopeArguments
import toothpick.config.Module

@Parcelize
data class SimpleTabScopeArguments(private val param: Int) : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        SimpleScopedTabModule(param)
    )
}
