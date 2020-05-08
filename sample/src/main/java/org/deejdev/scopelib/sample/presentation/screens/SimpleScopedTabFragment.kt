package org.deejdev.scopelib.sample.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import org.deejdev.scopelib.sample.R
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.SimpleScopedTabParam
import org.deejdev.scopelib.sample.presentation.base.BaseFragment
import org.deejdev.scopelib.sample.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class SimpleScopedTabFragment : BaseFragment(R.layout.fragment_simple_scoped_tab) {
    @BindView(R.id.content) lateinit var label: TextView

    @Inject @SimpleScopedTabParam lateinit var valueFromScope: String
    @Inject lateinit var coordinator: RootCoordinator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        label.text = valueFromScope
    }

    @OnClick(R.id.button)
    fun replaceChain() = coordinator.replacementChain()
}
