package org.deejdev.scopelib.sample.presentation.navigation.cicerone

import org.deejdev.scopelib.ScopeBlueprint
import org.deejdev.scopelib.ScopeModulesFactory
import org.deejdev.scopelib.sample.core.toothpick.scope.HomeScope
import org.deejdev.scopelib.sample.core.toothpick.scope.RootScope
import org.deejdev.scopelib.sample.core.toothpick.scope.SimpleTabScope
import org.deejdev.scopelib.sample.core.toothpick.scope.TabsScope
import org.deejdev.scopelib.sample.presentation.scopemodules.NestingScopeModulesFactory
import org.deejdev.scopelib.sample.presentation.scopemodules.SimpleTabScopeModulesFactory
import org.deejdev.scopelib.sample.presentation.screens.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import kotlin.random.Random
import kotlin.random.nextInt

object Screens {
    class Home : ScopeLibAppScreen() {
        override val usedScopeName get() = RootScope::class.java
        override fun createFragment() = HomeFragment()
    }

    class ScopedHome : ScopeLibAppScreen() {
        override fun createScopeBlueprint() = ScopeBlueprint<HomeScope, RootScope>(ScopeModulesFactory.Empty)
        override fun createFragment() = ScopedHomeFragment()
    }

    class SimpleTab : ScopeLibAppScreen() {
        override val usedScopeName get() = TabsScope::class.java
        override fun createFragment() = SimpleTabFragment()
    }

    class SimpleScopedTab : ScopeLibAppScreen() {
        override fun createScopeBlueprint() = ScopeBlueprint<SimpleTabScope, HomeScope>(
            SimpleTabScopeModulesFactory(Random.nextInt(0..100))
        )

        override fun createFragment() = SimpleScopedTabFragment()
    }

    object ItemList : SupportAppScreen() {
        override fun getFragment() = ItemListFragment()
    }

    object ItemDetails : SupportAppScreen() {
        override fun getFragment() = ItemDetailsFragment()
    }

    class Nesting(private val level: Int, private val parentScopeName: Any) : ScopeLibAppScreen() {
        private val modulesFactory = NestingScopeModulesFactory(level)

        override fun createScopeBlueprint() = ScopeBlueprint(
            "Nesting level $level ${modulesFactory.param}",
            modulesFactory,
            parentScopeName
        )

        override fun createFragment() = NestingFragment()
    }
}
