package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.thoroughtestapp.core.toothpick.scope.ScopeName
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
        override val scopeOptions = ScopeOptions(ScopeName.Home, ScopeArguments.Empty, ScopeName.Root, storeInPrevious = true)

        override fun getFragment() = ScopedHomeFragment().also {
            it.scopeOptions = scopeOptions
        }
    }

    object SimpleTab : SupportAppScreen() {
        override fun getFragment() = SimpleTabFragment()
    }

    class SimpleScopedTab(storeInPrevious: Boolean) : SupportAppScreen(), OpensScope2 {
        override val scopeOptions = ScopeOptions(
            ScopeName.SimpleTab,
            SimpleTabScopeArguments(Random.nextInt(0..100)),
            ScopeName.Home,
            storeInPrevious = storeInPrevious
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
