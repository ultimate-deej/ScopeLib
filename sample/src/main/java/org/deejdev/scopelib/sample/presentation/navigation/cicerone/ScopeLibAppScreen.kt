package org.deejdev.scopelib.sample.presentation.navigation.cicerone

import androidx.fragment.app.Fragment
import org.deejdev.scopelib.ScopeOptions
import org.deejdev.scopelib.attachScopeOptions
import org.deejdev.scopelib.useScope
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class ScopeLibAppScreen : SupportAppScreen() {
    open val scopeOptions: ScopeOptions? get() = null
    protected open val usedScopeName: Any? get() = null

    final override fun getFragment() = createFragment().apply {
        scopeOptions?.let { attachScopeOptions(it) }
        usedScopeName?.let { useScope(it) }
    }

    protected abstract fun createFragment(): Fragment
}
