package org.deejdev.scopelib.sample.presentation.navigation.cicerone

import androidx.fragment.app.Fragment
import org.deejdev.scopelib.ScopeBlueprint
import org.deejdev.scopelib.attachScopeBlueprint
import org.deejdev.scopelib.attachedScopeBlueprint
import org.deejdev.scopelib.useScope
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class ScopeLibAppScreen : SupportAppScreen() {
    protected open fun createScopeBlueprint(): ScopeBlueprint? = null
    protected open val usedScopeName: Any? get() = null

    private var called = false

    val attachedScopeBlueprint: ScopeBlueprint?
        get() = lazyFragment.attachedScopeBlueprint

    final override fun getFragment(): Fragment {
        check(!called) { "ScopeLibAppScreen instances are one-off (${javaClass.name})" }
        called = true
        return lazyFragment
    }

    private val lazyFragment by lazy(LazyThreadSafetyMode.NONE) {
        createFragment().apply {
            createScopeBlueprint()?.let { attachScopeBlueprint(it) }
            usedScopeName?.let { useScope(it) }
        }
    }

    protected abstract fun createFragment(): Fragment
}
