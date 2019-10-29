package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.thoroughtestapp.presentation.scopearguments.SimpleTabScopeArguments
import deej.thoroughtestapp.presentation.screens.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object Home : SupportAppScreen() {
        override fun getFragment() = HomeFragment()
    }

    object ScopedHome : SupportAppScreen() {
        override fun getFragment() = ScopedHomeFragment().apply {
            scopeOptions = ScopeOptions("Home scope", ScopeArguments.Empty, extends = true)
        }
    }

    object SimpleTab : SupportAppScreen() {
        override fun getFragment() = SimpleTabFragment()
    }

    object SimpleScopedTab : SupportAppScreen() {
        override fun getFragment() = SimpleScopedTabFragment().apply {
            scopeOptions = ScopeOptions("Simple tab scope", SimpleTabScopeArguments)
        }
    }

    object ItemList : SupportAppScreen() {
        override fun getFragment() = ItemListFragment()
    }

    object ItemDetails : SupportAppScreen() {
        override fun getFragment() = ItemDetailsFragment()
    }
}
