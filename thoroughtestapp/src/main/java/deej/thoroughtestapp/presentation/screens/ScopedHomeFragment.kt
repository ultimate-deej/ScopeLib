package deej.thoroughtestapp.presentation.screens

import butterknife.OnClick
import deej.scopelib.core.toothpick.scope.OpensScopeFragment
import deej.thoroughtestapp.R
import deej.thoroughtestapp.presentation.base.BaseFragment
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class ScopedHomeFragment : BaseFragment(R.layout.fragment_home), OpensScopeFragment {
    @Inject lateinit var coordinator: RootCoordinator

    @OnClick(R.id.button)
    fun goToTabs() = coordinator.tabs()
}
