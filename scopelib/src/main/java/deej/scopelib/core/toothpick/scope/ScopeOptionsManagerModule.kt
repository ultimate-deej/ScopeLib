package deej.scopelib.core.toothpick.scope

import toothpick.config.Module

class ScopeOptionsManagerModule(manager: ScopeOptionsManager) : Module() {
    init {
        bind(ScopeOptionsManager::class.java).toInstance(manager)
    }
}
