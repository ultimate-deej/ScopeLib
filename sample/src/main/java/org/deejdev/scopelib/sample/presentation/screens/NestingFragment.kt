package org.deejdev.scopelib.sample.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.commit
import butterknife.BindView
import butterknife.OnClick
import org.deejdev.scopelib.sample.R
import org.deejdev.scopelib.sample.core.toothpick.BoxedInt
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.NestingLevel
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.NestingNavigation
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.NestingParam
import org.deejdev.scopelib.sample.presentation.base.BaseFragment
import org.deejdev.scopelib.sample.presentation.navigation.cicerone.Screens
import ru.terrakok.cicerone.Router
import toothpick.Scope
import javax.inject.Inject

class NestingFragment : BaseFragment(R.layout.fragment_nesting) {
    @BindView(R.id.back) lateinit var backButton: Button
    @BindView(R.id.nest) lateinit var nestButton: Button
    @BindView(R.id.text) lateinit var textView: TextView

    @Inject @NestingParam lateinit var param: String
    @Inject @NestingLevel lateinit var boxedLevel: BoxedInt
    @Inject lateinit var scope: Scope
    @Inject @NestingNavigation lateinit var router: Router

    private val level: Int get() = boxedLevel.toInt()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = "Level $level ($param)"
        childFragmentManager.addOnBackStackChangedListener {
            updateButtons()
        }
        updateButtons()
    }

    @OnClick(R.id.nest)
    fun nest() {
        childFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.content, Screens.Nesting(level + 1, scope.name).fragment)
        }
    }

    @OnClick(R.id.back)
    fun back() {
        childFragmentManager.popBackStack()
    }

    private fun updateButtons() {
        val hasChildren = childFragmentManager.backStackEntryCount > 0
        backButton.isEnabled = hasChildren
    }
}
