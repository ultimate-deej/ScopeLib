package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.scopelib.core.toothpick.scope.attachScopeOptions
import deej.thoroughtestapp.core.toothpick.scope.HomeScope
import deej.thoroughtestapp.core.toothpick.scope.TabsScope
import deej.thoroughtestapp.presentation.scopearguments.TabsScopeArguments
import deej.thoroughtestapp.presentation.screens.ListFlowFragment
import deej.thoroughtestapp.presentation.screens.TabsFlowFragment

object Flows {
    class Tabs : ScopeLibAppScreen() {
        override val scopeOptions = ScopeOptions(TabsScope::class.java, TabsScopeArguments("Injected Tabs Param"), HomeScope::class.java, managedByParent = true)

        override fun getFragment() = TabsFlowFragment().also {
            it.attachScopeOptions(scopeOptions)
        }
    }

    object List : ScopeLibAppScreen() {
        override fun getFragment() = ListFlowFragment()
    }
}
