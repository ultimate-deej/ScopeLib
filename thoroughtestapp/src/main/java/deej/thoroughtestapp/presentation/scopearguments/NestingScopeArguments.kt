package deej.thoroughtestapp.presentation.scopearguments

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.thoroughtestapp.core.toothpick.modules.NavigationModule
import deej.thoroughtestapp.core.toothpick.modules.NestingModule
import deej.thoroughtestapp.core.toothpick.qualifiers.NestingNavigation
import kotlinx.android.parcel.Parcelize
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
