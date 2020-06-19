package org.deejdev.scopelib.sample.presentation.navigation.cicerone

import androidx.fragment.app.Fragment
import org.deejdev.scopelib.ScopeOptions
import org.deejdev.scopelib.attachScopeOptions
import org.deejdev.scopelib.useScope
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class ScopeLibAppScreen : SupportAppScreen() {
    open val scopeOptions: ScopeOptions? get() = null
    protected open val usedScopeName: Any? get() = null

    private var called = false

    final override fun getFragment(): Fragment {
        check(!called) { "ScopeLibAppScreen instances are one-off (${javaClass.name})" }
        called = true
        return createFragment().apply {
            scopeOptions?.let { attachScopeOptions(it) }
            usedScopeName?.let { useScope(it) }
        }
    }

    protected abstract fun createFragment(): Fragment
}
