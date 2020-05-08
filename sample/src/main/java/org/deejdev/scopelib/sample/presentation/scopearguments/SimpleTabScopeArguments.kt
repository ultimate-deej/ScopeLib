package org.deejdev.scopelib.sample.presentation.scopearguments

import kotlinx.android.parcel.Parcelize
import org.deejdev.scopelib.ScopeArguments
import org.deejdev.scopelib.sample.core.toothpick.modules.SimpleScopedTabModule
import toothpick.config.Module

@Parcelize
data class SimpleTabScopeArguments(private val param: Int) : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        SimpleScopedTabModule(param)
    )
}
