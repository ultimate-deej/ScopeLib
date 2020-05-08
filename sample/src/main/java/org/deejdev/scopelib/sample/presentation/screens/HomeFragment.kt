package org.deejdev.scopelib.sample.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.OnClick
import org.deejdev.scopelib.sample.R
import org.deejdev.scopelib.sample.presentation.base.BaseFragment
import org.deejdev.scopelib.sample.presentation.navigation.coordinators.RootCoordinator
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    @BindView(R.id.button) lateinit var button: Button

    @Inject lateinit var coordinator: RootCoordinator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.text = "Go to scoped home"
    }

    @OnClick(R.id.button)
    fun goToTabs() = coordinator.home2()
}
