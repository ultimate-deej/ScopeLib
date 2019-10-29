package deej.thoroughtestapp.presentation.navigation.coordinators

import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.thoroughtestapp.core.toothpick.qualifiers.ActivityNavigation
import deej.thoroughtestapp.presentation.navigation.cicerone.Flows
import deej.thoroughtestapp.presentation.navigation.cicerone.Screens
import ru.terrakok.cicerone.Router
import toothpick.InjectConstructor

@InjectConstructor
class RootCoordinator(
    private val scopeOptions: ScopeOptions,
    @ActivityNavigation private val router: Router
) {
    fun home() = router.newRootScreen(Screens.Home)

    fun home2() = router.navigateTo(Screens.ScopedHome)

    fun tabs() = router.navigateTo(Flows.Tabs)

    fun tabsViaNewChain() = router.newRootChain(Screens.Home, Screens.ScopedHome, Flows.Tabs)

    fun replacementChain() = router.newRootChain(Screens.Home, Screens.SimpleScopedTab)
}
