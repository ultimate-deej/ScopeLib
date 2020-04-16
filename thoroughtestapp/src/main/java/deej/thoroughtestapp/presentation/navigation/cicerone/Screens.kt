package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.scopelib.core.toothpick.scope.scopeOptions
import deej.scopelib.core.toothpick.scope.usedScopeName
import deej.thoroughtestapp.core.toothpick.scope.HomeScope
import deej.thoroughtestapp.core.toothpick.scope.RootScope
import deej.thoroughtestapp.core.toothpick.scope.SimpleTabScope
import deej.thoroughtestapp.core.toothpick.scope.TabsScope
import deej.thoroughtestapp.presentation.scopearguments.SimpleTabScopeArguments
import deej.thoroughtestapp.presentation.screens.*
import kotlin.random.Random
import kotlin.random.nextInt

object Screens {
    object Home : ScopeLibAppScreen() {
        override fun getFragment() = HomeFragment().also {
            it.usedScopeName = RootScope::class.java
        }
    }

    class ScopedHome : ScopeLibAppScreen() {
        override val scopeOptions = ScopeOptions(HomeScope::class.java, ScopeArguments.Empty, RootScope::class.java, managedByParent = true)

        override fun getFragment() = ScopedHomeFragment().also {
            it.scopeOptions = scopeOptions
            it.usedScopeName = scopeOptions.name
        }
    }

    object SimpleTab : ScopeLibAppScreen() {
        override fun getFragment() = SimpleTabFragment().also {
            it.usedScopeName = TabsScope::class.java
        }
    }

    class SimpleScopedTab(managedByParent: Boolean) : ScopeLibAppScreen() {
        override val scopeOptions = ScopeOptions(
            SimpleTabScope::class.java,
            SimpleTabScopeArguments(Random.nextInt(0..100)),
            HomeScope::class.java,
            managedByParent = managedByParent
        )

        override fun getFragment() = SimpleScopedTabFragment().also {
            it.scopeOptions = scopeOptions
            it.usedScopeName = scopeOptions.name
        }
    }

    object ItemList : ScopeLibAppScreen() {
        override fun getFragment() = ItemListFragment()
    }

    object ItemDetails : ScopeLibAppScreen() {
        override fun getFragment() = ItemDetailsFragment()
    }
}
