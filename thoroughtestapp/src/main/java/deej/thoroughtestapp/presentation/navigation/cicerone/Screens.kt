package deej.thoroughtestapp.presentation.navigation.cicerone

import deej.thoroughtestapp.presentation.screens.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object Home : SupportAppScreen() {
        override fun getFragment() = HomeFragment()
    }

    object SimpleTab : SupportAppScreen() {
        override fun getFragment() = SimpleTabFragment()
    }

    object SimpleScopedTab : SupportAppScreen() {
        override fun getFragment() = SimpleScopedTabFragment()
    }

    object ItemList : SupportAppScreen() {
        override fun getFragment() = ItemListFragment()
    }

    object ItemDetails : SupportAppScreen() {
        override fun getFragment() = ItemDetailsFragment()
    }
}
