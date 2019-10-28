package deej.thoroughtestapp.presentation.screens

import butterknife.OnClick
import deej.thoroughtestapp.R
import deej.thoroughtestapp.presentation.base.BaseFragment
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    @Inject lateinit var coordinator: RootCoordinator

    @OnClick(R.id.button)
    fun goToTabs() = coordinator.tabs()
}
