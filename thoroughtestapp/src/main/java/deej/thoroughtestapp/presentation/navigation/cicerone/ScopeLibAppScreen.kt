package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeOptions
import ru.terrakok.cicerone.android.support.SupportAppScreen

open class ScopeLibAppScreen : SupportAppScreen() {
    open val scopeOptions: ScopeOptions? get() = null
}
