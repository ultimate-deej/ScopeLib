package org.deejdev.scopelib.sample.presentation.scopearguments

import kotlinx.android.parcel.Parcelize
import org.deejdev.scopelib.ScopeArguments
import org.deejdev.scopelib.sample.core.toothpick.modules.NavigationModule
import org.deejdev.scopelib.sample.core.toothpick.modules.NestingModule
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.NestingNavigation
import toothpick.config.Module

@Parcelize
data class NestingScopeArguments(
    val level: Int,
    val param: String = randomString()
) : ScopeArguments() {
    override fun createModules(): Array<Module> = arrayOf(
        NestingModule(level, param),
        NavigationModule<NestingNavigation>(false)
    )
}

private fun randomString(): String {
    val chars: List<Char> = ('A'..'Z') + ('a'..'z')
    return String(CharArray(3) { chars.random() })
}
