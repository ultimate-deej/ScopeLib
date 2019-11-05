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

    // TODO: prepare scope options chain before navigating
    //  or, move the logic out of callbacks and only call close there (in onDestroy), and do open routines in navigator callback
    fun replacementChain() = router.newRootChain(Screens.Home, Screens.ScopedHome, Screens.SimpleScopedTab)
}
