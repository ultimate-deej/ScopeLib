package deej.thoroughtestapp.presentation.screens

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import butterknife.BindView
import com.google.android.material.bottomnavigation.BottomNavigationView
import deej.thoroughtestapp.R
import deej.thoroughtestapp.core.toothpick.qualifiers.TabsParam
import deej.thoroughtestapp.presentation.base.BaseFragment
import deej.thoroughtestapp.presentation.navigation.cicerone.Flows
import deej.thoroughtestapp.presentation.navigation.cicerone.Screens
import toothpick.Scope
import javax.inject.Inject

class TabsFlowFragment : BaseFragment(R.layout.flow_tabs), BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.title) lateinit var titleLabel: TextView
    @BindView(R.id.bottomNavigation) lateinit var bottomNavigationView: BottomNavigationView

    @Inject @TabsParam lateinit var paramFromScope: String
    @Inject lateinit var scope: Scope

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleLabel.text = paramFromScope
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.setOnNavigationItemReselectedListener { }

        if (childFragmentManager.findFragmentById(R.id.content) == null) {
            setCurrentTabById(bottomNavigationView.selectedItemId)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        setCurrentTabById(item.itemId)
        return true
    }

    private fun setCurrentTabById(@IdRes itemId: Int) {
        when (itemId) {
            R.id.simple -> setCurrentFragment(FRAGMENT_SIMPLE) { Screens.SimpleTab.fragment }
            R.id.scoped -> setCurrentFragment(FRAGMENT_SIMPLE_SCOPED) { Screens.SimpleScopedTab().fragment }
            R.id.list -> setCurrentFragment(FRAGMENT_LIST) { Flows.List.fragment }
            R.id.nesting -> setCurrentFragment(FRAGMENT_NESTING) { Screens.Nesting(0, scope.name).fragment }
        }
    }

    private fun setCurrentFragment(tag: String, fragmentFactory: () -> Fragment) {
        val currentFragment = childFragmentManager.findFragmentById(R.id.content)
        val existingInstance = childFragmentManager.findFragmentByTag(tag)

        childFragmentManager.commitNow {
            setReorderingAllowed(true)

            if (currentFragment != null) {
                detach(currentFragment)
            }

            if (existingInstance == null) {
                add(R.id.content, fragmentFactory(), tag)
            } else {
                attach(existingInstance)
            }
        }
    }

    companion object {
        private const val FRAGMENT_SIMPLE = "Simple"
        private const val FRAGMENT_SIMPLE_SCOPED = "Simple scoped"
        private const val FRAGMENT_LIST = "List"
        private const val FRAGMENT_NESTING = "Nesting"
    }
}
