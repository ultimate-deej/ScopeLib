package deej.thoroughtestapp.presentation.screens

import butterknife.OnClick
import deej.scopelib.core.toothpick.scope.UsesScope
import deej.thoroughtestapp.R
import deej.thoroughtestapp.core.toothpick.scope.TabsScope
import deej.thoroughtestapp.presentation.base.BaseFragment
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class SimpleTabFragment : BaseFragment(R.layout.fragment_simple_tab), UsesScope {
    override val usedScopeName get() = TabsScope::class.java

    @Inject lateinit var coordinator: RootCoordinator

    @OnClick(R.id.button)
    fun replaceChain() = coordinator.tabsViaNewChain()
}
