package deej.thoroughtestapp.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import deej.scopelib.core.toothpick.scope.UsesScope
import deej.thoroughtestapp.R
import deej.thoroughtestapp.core.toothpick.scope.RootScope
import deej.thoroughtestapp.presentation.base.BaseFragment
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home), UsesScope {
    override val usedScopeName get() = RootScope::class.java

    @BindView(R.id.button) lateinit var button: Button

    @Inject lateinit var coordinator: RootCoordinator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.text = "Go to scoped home"
    }

    @OnClick(R.id.button)
    fun goToTabs() = coordinator.home2()
}
