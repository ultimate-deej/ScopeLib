package org.deejdev.scopelib

import toothpick.config.Module

class ScopeOptionsManagerModule(manager: ScopeOptionsManager) : Module() {
    init {
        bind(ScopeOptionsManager::class.java).toInstance(manager)
    }
}
