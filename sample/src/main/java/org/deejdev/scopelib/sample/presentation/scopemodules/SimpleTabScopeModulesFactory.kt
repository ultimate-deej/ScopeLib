package org.deejdev.scopelib.sample.presentation.scopemodules

import kotlinx.parcelize.Parcelize
import org.deejdev.scopelib.ScopeModulesFactory
import org.deejdev.scopelib.sample.core.toothpick.modules.SimpleScopedTabModule
import toothpick.config.Module

@Parcelize
data class SimpleTabScopeModulesFactory(private val param: Int) : ScopeModulesFactory() {
    override fun createModules(): Array<Module> = arrayOf(
        SimpleScopedTabModule(param)
    )
}
