package org.deejdev.scopelib

import toothpick.config.Module

class ScopeOptionsModule(scopeOptions: ScopeOptions) : Module() {
    init {
        bind(ScopeOptions::class.java).toInstance(scopeOptions)
    }
}
