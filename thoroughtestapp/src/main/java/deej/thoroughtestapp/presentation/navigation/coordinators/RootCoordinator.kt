package deej.thoroughtestapp.presentation.navigation.coordinators

import deej.thoroughtestapp.core.toothpick.qualifiers.ActivityNavigation
import deej.thoroughtestapp.presentation.navigation.cicerone.Screens
import ru.terrakok.cicerone.Router
import toothpick.InjectConstructor

@InjectConstructor
class RootCoordinator(
    @ActivityNavigation private val router: Router
) {
    fun home() = router.newRootScreen(Screens.Home)
}