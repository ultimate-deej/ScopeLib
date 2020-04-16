package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.scopelib.core.toothpick.scope.scopeOptions
import deej.thoroughtestapp.core.toothpick.scope.HomeScope
import deej.thoroughtestapp.core.toothpick.scope.RootScope
import deej.thoroughtestapp.core.toothpick.scope.SimpleTabScope
import deej.thoroughtestapp.presentation.scopearguments.SimpleTabScopeArguments
import deej.thoroughtestapp.presentation.screens.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import kotlin.random.Random
import kotlin.random.nextInt

object Screens {
    object Home : SupportAppScreen() {
        override fun getFragment() = HomeFragment()
    }

    class ScopedHome : SupportAppScreen(), OpensScope2 {
        override val scopeOptions = ScopeOptions(HomeScope::class.java, ScopeArguments.Empty, RootScope::class.java, managedByParent = true)

        override fun getFragment() = ScopedHomeFragment().also {
            it.scopeOptions = scopeOptions
        }
    }

    object SimpleTab : SupportAppScreen() {
        override fun getFragment() = SimpleTabFragment()
    }

    class SimpleScopedTab(managedByParent: Boolean) : SupportAppScreen(), OpensScope2 {
        override val scopeOptions = ScopeOptions(
            SimpleTabScope::class.java,
            SimpleTabScopeArguments(Random.nextInt(0..100)),
            HomeScope::class.java,
            managedByParent = managedByParent
        )

        override fun getFragment() = SimpleScopedTabFragment().also {
            it.scopeOptions = scopeOptions
        }
    }

    object ItemList : SupportAppScreen() {
        override fun getFragment() = ItemListFragment()
    }

    object ItemDetails : SupportAppScreen() {
        override fun getFragment() = ItemDetailsFragment()
    }
}

// TODO: Make the normal OpensScope one not implement UsesScope, and delete this interface
interface OpensScope2 {
    val scopeOptions: ScopeOptions
}
