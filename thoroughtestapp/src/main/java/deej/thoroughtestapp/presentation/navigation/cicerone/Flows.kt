package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.thoroughtestapp.presentation.screens.ListFlowFragment
import deej.thoroughtestapp.presentation.screens.TabsFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Flows {
    object Tabs : SupportAppScreen() {
        override fun getFragment() = TabsFlowFragment()
    }

    object List : SupportAppScreen() {
        override fun getFragment() = ListFlowFragment()
    }
}
