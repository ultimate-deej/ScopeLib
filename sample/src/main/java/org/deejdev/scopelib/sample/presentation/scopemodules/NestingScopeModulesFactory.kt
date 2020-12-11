package org.deejdev.scopelib.sample.presentation.scopemodules

import kotlinx.parcelize.Parcelize
import org.deejdev.scopelib.ScopeModulesFactory
import org.deejdev.scopelib.sample.core.toothpick.modules.NavigationModule
import org.deejdev.scopelib.sample.core.toothpick.modules.NestingModule
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.NestingNavigation
import toothpick.Scope
import toothpick.config.Module

@Parcelize
data class NestingScopeModulesFactory(
    val level: Int,
    val param: String = randomString()
) : ScopeModulesFactory() {
    override fun createModules(scope: Scope): Array<Module> = arrayOf(
        NestingModule(level, param),
        NavigationModule<NestingNavigation>(false)
    )
}

private fun randomString(): String {
    val chars: List<Char> = ('A'..'Z') + ('a'..'z')
    return String(CharArray(3) { chars.random() })
}
