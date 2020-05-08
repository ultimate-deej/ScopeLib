package deej.thoroughtestapp.presentation.navigation.cicerone

import androidx.fragment.app.Fragment
import deej.scopelib.core.toothpick.scope.ScopeOptions
import deej.scopelib.core.toothpick.scope.attachScopeOptions
import deej.scopelib.core.toothpick.scope.useScope
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class ScopeLibAppScreen : SupportAppScreen() {
    open val scopeOptions: ScopeOptions? get() = null
    protected open val usedScopeName: Any? get() = scopeOptions?.name

    final override fun getFragment() = createFragment().apply {
        scopeOptions?.let { attachScopeOptions(it) }
        useScope(usedScopeName)
    }

    protected abstract fun createFragment(): Fragment
}
