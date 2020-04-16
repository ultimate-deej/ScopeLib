package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.thoroughtestapp.core.toothpick.scope.HomeScope
import deej.thoroughtestapp.core.toothpick.scope.TabsScope
import deej.thoroughtestapp.presentation.scopearguments.TabsScopeArguments
import deej.thoroughtestapp.presentation.screens.ListFlowFragment
import deej.thoroughtestapp.presentation.screens.TabsFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Flows {
    class Tabs : SupportAppScreen(), OpensScope2 {
        override val scopeOptions = ScopeOptions(TabsScope::class.java, TabsScopeArguments("Injected Tabs Param"), HomeScope::class.java, managedByParent = true)

        override fun getFragment() = TabsFlowFragment().also {
            it.scopeOptions = scopeOptions
        }
    }

    object List : SupportAppScreen() {
        override fun getFragment() = ListFlowFragment()
    }
}
