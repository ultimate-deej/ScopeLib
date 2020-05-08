package org.deejdev.scopelib.sample.presentation.navigation.cicerone

import org.deejdev.scopelib.ScopeOptions
import org.deejdev.scopelib.sample.core.toothpick.scope.HomeScope
import org.deejdev.scopelib.sample.core.toothpick.scope.TabsScope
import org.deejdev.scopelib.sample.presentation.scopearguments.TabsScopeArguments
import org.deejdev.scopelib.sample.presentation.screens.ListFlowFragment
import org.deejdev.scopelib.sample.presentation.screens.TabsFlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Flows {
    class Tabs : ScopeLibAppScreen() {
        override val scopeOptions = ScopeOptions.withUniqueId(TabsScope::class.java, TabsScopeArguments("Injected Tabs Param"), HomeScope::class.java)
        override fun createFragment() = TabsFlowFragment()
    }

    object List : SupportAppScreen() {
        override fun getFragment() = ListFlowFragment()
    }
}
