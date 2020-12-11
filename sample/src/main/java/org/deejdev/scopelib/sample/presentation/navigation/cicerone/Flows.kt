package org.deejdev.scopelib.sample.presentation.navigation.cicerone

import org.deejdev.scopelib.ScopeBlueprint
import org.deejdev.scopelib.sample.core.toothpick.scope.HomeScope
import org.deejdev.scopelib.sample.core.toothpick.scope.TabsScope
import org.deejdev.scopelib.sample.presentation.scopemodules.TabsScopeModulesFactory
import org.deejdev.scopelib.sample.presentation.screens.ListFlowFragment
import org.deejdev.scopelib.sample.presentation.screens.TabsFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Flows {
    class Tabs : ScopeLibAppScreen() {
        override fun createScopeBlueprint() = ScopeBlueprint<TabsScope, HomeScope>(TabsScopeModulesFactory("Injected Tabs Param"))
        override fun createFragment() = TabsFlowFragment()
    }

    object List : SupportAppScreen() {
        override fun getFragment() = ListFlowFragment()
    }
}
