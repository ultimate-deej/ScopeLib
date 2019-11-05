package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.thoroughtestapp.core.platform.MainActivity
import deej.thoroughtestapp.presentation.scopearguments.TabsScopeArguments
import deej.thoroughtestapp.presentation.screens.ListFlowFragment
import deej.thoroughtestapp.presentation.screens.TabsFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Flows {
    object Tabs : SupportAppScreen() {
        override fun getFragment() = TabsFlowFragment().apply {
            scopeOptions = ScopeOptions("Tabs scope", TabsScopeArguments("Injected Tabs Param"), MainActivity::class.java.name, storeInParent = true)
        }
    }

    object List : SupportAppScreen() {
        override fun getFragment() = ListFlowFragment()
    }
}
