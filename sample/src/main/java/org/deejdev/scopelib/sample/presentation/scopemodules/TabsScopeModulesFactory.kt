package org.deejdev.scopelib.sample.presentation.scopemodules

import kotlinx.parcelize.Parcelize
import org.deejdev.scopelib.ScopeModulesFactory
import org.deejdev.scopelib.sample.core.toothpick.modules.TabsModule
import toothpick.Scope
import toothpick.config.Module

@Parcelize
data class TabsScopeModulesFactory(
    val param: String
) : ScopeModulesFactory() {
    override fun createModules(scope: Scope): Array<Module> = arrayOf(
        TabsModule(param)
    )
}
