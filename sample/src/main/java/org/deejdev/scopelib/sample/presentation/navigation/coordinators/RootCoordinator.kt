package org.deejdev.scopelib.sample.presentation.navigation.coordinators

import org.deejdev.scopelib.ScopeOptionsManager
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.ActivityNavigation
import org.deejdev.scopelib.sample.core.toothpick.scope.RootScope
import org.deejdev.scopelib.sample.presentation.navigation.cicerone.Flows
import org.deejdev.scopelib.sample.presentation.navigation.cicerone.Screens
import ru.terrakok.cicerone.Router
import toothpick.InjectConstructor

@RootScope
@InjectConstructor
class RootCoordinator(
    private val scopeOptionsManager: ScopeOptionsManager,
    @ActivityNavigation private val router: Router
) {
    fun home() = router.newRootScreen(Screens.Home())

    fun home2() = router.navigateTo(Screens.ScopedHome())

    fun tabs() = router.navigateTo(Flows.Tabs())

    fun tabsViaNewChain() {
        val screens = arrayOf(Screens.Home(), Screens.ScopedHome(), Flows.Tabs())
        for (screen in screens) {
            screen.attachedScopeOptions?.let(scopeOptionsManager::overwrite)
        }
        router.newRootChain(*screens)
    }

    fun replacementChain() {
        val screens = arrayOf(Screens.Home(), Screens.ScopedHome(), Screens.SimpleScopedTab())
        for (screen in screens) {
            screen.attachedScopeOptions?.let(scopeOptionsManager::overwrite)
        }

        router.newRootChain(*screens)
    }
}
