package org.deejdev.scopelib.sample.presentation.screens

import butterknife.OnClick
import org.deejdev.scopelib.sample.R
import org.deejdev.scopelib.sample.presentation.base.BaseFragment
import org.deejdev.scopelib.sample.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class SimpleTabFragment : BaseFragment(R.layout.fragment_simple_tab) {
    @Inject lateinit var coordinator: RootCoordinator

    @OnClick(R.id.button)
    fun replaceChain() = coordinator.tabsViaNewChain()
}
