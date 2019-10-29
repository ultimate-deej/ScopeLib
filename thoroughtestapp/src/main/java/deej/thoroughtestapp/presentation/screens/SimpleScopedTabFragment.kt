package deej.thoroughtestapp.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import deej.scopelib.core.toothpick.scope.OpensScopeFragment
import deej.thoroughtestapp.R
import deej.thoroughtestapp.core.toothpick.qualifiers.SimpleScopedTabParam
import deej.thoroughtestapp.presentation.base.BaseFragment
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class SimpleScopedTabFragment : BaseFragment(R.layout.fragment_simple_scoped_tab), OpensScopeFragment {
    @BindView(R.id.content) lateinit var label: TextView

    @Inject @SimpleScopedTabParam lateinit var valueFromScope: String
    @Inject lateinit var coordinator: RootCoordinator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        label.text = valueFromScope
    }

    @OnClick(R.id.button)
    fun replaceChain() = coordinator.replacementChain()
}
