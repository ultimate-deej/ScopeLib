package deej.thoroughtestapp.presentation.navigation.coordinators

import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.thoroughtestapp.core.toothpick.qualifiers.ActivityNavigation
import deej.thoroughtestapp.core.toothpick.scope.RootScope
import deej.thoroughtestapp.presentation.navigation.cicerone.Flows
import deej.thoroughtestapp.presentation.navigation.cicerone.OpensScope2
import deej.thoroughtestapp.presentation.navigation.cicerone.Screens
import ru.terrakok.cicerone.Router
import toothpick.InjectConstructor

@RootScope
@InjectConstructor
class RootCoordinator(
    private val scopeOptions: ScopeOptions,
    @ActivityNavigation private val router: Router
) {
    fun home() = router.newRootScreen(Screens.Home)

    fun home2() = router.navigateTo(Screens.ScopedHome())

    fun tabs() = router.navigateTo(Flows.Tabs())

    fun tabsViaNewChain() {
        val screens = arrayOf(Screens.Home, Screens.ScopedHome(), Flows.Tabs())
        screens.mapNotNull { (it as? OpensScope2)?.scopeOptions }.forEach {
            scopeOptions.removeStartingFrom(it.name, null)
            scopeOptions.appendTail(it)
        }
        router.newRootChain(*screens)
    }

    fun replacementChain() {
        val screens = arrayOf(Screens.Home, Screens.ScopedHome(), Screens.SimpleScopedTab(true))
        // TODO: Here I'm doing something similar to what InjectorFragmentLifecycleCallbacks does
        //  Maybe it's reasonable to move such high-level methods to ScopeController? Or not, it's not that complicated here.
        screens.mapNotNull { (it as? OpensScope2)?.scopeOptions }.forEach {
            scopeOptions.removeStartingFrom(it.name, null)
            scopeOptions.appendTail(it)
        }

        router.newRootChain(*screens)
    }
}
