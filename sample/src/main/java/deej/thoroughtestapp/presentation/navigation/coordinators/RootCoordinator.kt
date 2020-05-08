package deej.thoroughtestapp.presentation.navigation.coordinators

import deej.thoroughtestapp.core.toothpick.qualifiers.ActivityNavigation
import deej.thoroughtestapp.core.toothpick.scope.RootScope
import deej.thoroughtestapp.presentation.navigation.cicerone.Flows
import deej.thoroughtestapp.presentation.navigation.cicerone.Screens
import org.deejdev.scopelib.ScopeOptionsManager
import ru.terrakok.cicerone.Router
import toothpick.InjectConstructor

@RootScope
@InjectConstructor
class RootCoordinator(
    private val scopeOptionsManager: ScopeOptionsManager,
    @ActivityNavigation private val router: Router
) {
    fun home() = router.newRootScreen(Screens.Home)

    fun home2() = router.navigateTo(Screens.ScopedHome())

    fun tabs() = router.navigateTo(Flows.Tabs())

    fun tabsViaNewChain() {
        val screens = arrayOf(Screens.Home, Screens.ScopedHome(), Flows.Tabs())
        for (screen in screens) {
            screen.scopeOptions?.let(scopeOptionsManager::overwrite)
        }
        router.newRootChain(*screens)
    }

    fun replacementChain() {
        val screens = arrayOf(Screens.Home, Screens.ScopedHome(), Screens.SimpleScopedTab())
        for (screen in screens) {
            screen.scopeOptions?.let(scopeOptionsManager::overwrite)
        }

        router.newRootChain(*screens)
    }
}
