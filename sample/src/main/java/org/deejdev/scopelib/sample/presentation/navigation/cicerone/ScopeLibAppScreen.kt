package org.deejdev.scopelib.sample.presentation.navigation.cicerone

import androidx.fragment.app.Fragment
import org.deejdev.scopelib.ScopeOptions
import org.deejdev.scopelib.attachScopeOptions
import org.deejdev.scopelib.attachedScopeOptions
import org.deejdev.scopelib.useScope
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class ScopeLibAppScreen : SupportAppScreen() {
    protected open fun createScopeOptions(): ScopeOptions? = null
    protected open val usedScopeName: Any? get() = null

    private var called = false

    val attachedScopeOptions: ScopeOptions?
        get() = lazyFragment.attachedScopeOptions

    final override fun getFragment(): Fragment {
        check(!called) { "ScopeLibAppScreen instances are one-off (${javaClass.name})" }
        called = true
        return lazyFragment
    }

    private val lazyFragment by lazy(LazyThreadSafetyMode.NONE) {
        createFragment().apply {
            createScopeOptions()?.let { attachScopeOptions(it) }
            usedScopeName?.let { useScope(it) }
        }
    }

    protected abstract fun createFragment(): Fragment
}
