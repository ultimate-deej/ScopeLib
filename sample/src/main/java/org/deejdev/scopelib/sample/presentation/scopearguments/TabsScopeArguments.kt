package org.deejdev.scopelib.sample.presentation.scopearguments

import kotlinx.android.parcel.Parcelize
import org.deejdev.scopelib.ScopeArguments
import org.deejdev.scopelib.sample.core.toothpick.modules.TabsModule
import toothpick.config.Module

@Parcelize
data class TabsScopeArguments(
    val param: String
) : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        TabsModule(param)
    )
}
