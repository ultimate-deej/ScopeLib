package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.thoroughtestapp.core.toothpick.scope.HomeScope
import deej.thoroughtestapp.core.toothpick.scope.RootScope
import deej.thoroughtestapp.core.toothpick.scope.SimpleTabScope
import deej.thoroughtestapp.core.toothpick.scope.TabsScope
import deej.thoroughtestapp.presentation.scopearguments.SimpleTabScopeArguments
import deej.thoroughtestapp.presentation.screens.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import kotlin.random.Random
import kotlin.random.nextInt

object Screens {
    object Home : ScopeLibAppScreen() {
        override val usedScopeName get() = RootScope::class.java
        override fun createFragment() = HomeFragment()
    }

    class ScopedHome : ScopeLibAppScreen() {
        override val scopeOptions = ScopeOptions.withUniqueId(HomeScope::class.java, ScopeArguments.Empty, RootScope::class.java)
        override fun createFragment() = ScopedHomeFragment()
    }

    object SimpleTab : ScopeLibAppScreen() {
        override val usedScopeName get() = TabsScope::class.java
        override fun createFragment() = SimpleTabFragment()
    }

    class SimpleScopedTab : ScopeLibAppScreen() {
        override val scopeOptions = ScopeOptions.withUniqueId(
            SimpleTabScope::class.java,
            SimpleTabScopeArguments(Random.nextInt(0..100)),
            HomeScope::class.java
        )

        override fun createFragment() = SimpleScopedTabFragment()
    }

    object ItemList : SupportAppScreen() {
        override fun getFragment() = ItemListFragment()
    }

    object ItemDetails : SupportAppScreen() {
        override fun getFragment() = ItemDetailsFragment()
    }

    object Nesting : SupportAppScreen() {
        override fun getFragment() = NestingFragment()
    }
}
