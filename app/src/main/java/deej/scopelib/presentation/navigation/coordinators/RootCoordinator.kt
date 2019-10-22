package deej.scopelib.presentation.navigation.coordinators

import deej.scopelib.core.toothpick.qualifiers.AppNavigation
import deej.scopelib.presentation.navigation.cicerone.Screens
import ru.terrakok.cicerone.Router
import toothpick.InjectConstructor

@InjectConstructor
class RootCoordinator(
    @AppNavigation private val router: Router
) {
    fun start(param: String) = router.newRootScreen(Screens.Home(param))
}
