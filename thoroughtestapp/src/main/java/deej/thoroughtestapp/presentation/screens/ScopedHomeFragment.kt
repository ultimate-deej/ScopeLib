package deej.thoroughtestapp.presentation.screens

import butterknife.OnClick
import deej.scopelib.core.toothpick.scope.UsesScope
import deej.thoroughtestapp.R
import deej.thoroughtestapp.core.toothpick.scope.HomeScope
import deej.thoroughtestapp.presentation.base.BaseFragment
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class ScopedHomeFragment : BaseFragment(R.layout.fragment_home), UsesScope {
    override val usedScopeName = HomeScope::class.java

    @Inject lateinit var coordinator: RootCoordinator

    @OnClick(R.id.button)
    fun goToTabs() = coordinator.tabs()
}
