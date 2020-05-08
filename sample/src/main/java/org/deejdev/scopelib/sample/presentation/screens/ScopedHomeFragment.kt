package org.deejdev.scopelib.sample.presentation.screens

import butterknife.OnClick
import org.deejdev.scopelib.sample.R
import org.deejdev.scopelib.sample.presentation.base.BaseFragment
import org.deejdev.scopelib.sample.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class ScopedHomeFragment : BaseFragment(R.layout.fragment_home) {
    @Inject lateinit var coordinator: RootCoordinator

    @OnClick(R.id.button)
    fun goToTabs() = coordinator.tabs()
}
