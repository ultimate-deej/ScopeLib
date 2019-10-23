package deej.scopelib.presentation.navigation.cicerone

import deej.scopelib.presentation.screens.HomeFragment
import deej.scopelib.presentation.screens.HomeScopeArguments
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    class Home(private val param: String) : SupportAppScreen() {
        override fun getFragment() = HomeFragment().apply {
            scopeArguments = HomeScopeArguments(this@Home.param)
        }
    }
}
