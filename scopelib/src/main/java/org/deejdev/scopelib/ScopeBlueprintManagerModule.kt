package org.deejdev.scopelib

import toothpick.config.Module

class ScopeBlueprintManagerModule(manager: ScopeBlueprintManager) : Module() {
    init {
        bind(ScopeBlueprintManager::class.java).toInstance(manager)
    }
}
