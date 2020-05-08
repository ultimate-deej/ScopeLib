package deej.scopelib.core.toothpick.scope

import toothpick.config.Module

class ScopeOptionsModule(scopeOptions: ScopeOptions) : Module() {
    init {
        bind(ScopeOptions::class.java).toInstance(scopeOptions)
    }
}
