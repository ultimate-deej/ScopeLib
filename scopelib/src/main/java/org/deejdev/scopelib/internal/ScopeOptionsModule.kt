package org.deejdev.scopelib.internal

import org.deejdev.scopelib.ScopeOptions
import toothpick.config.Module

internal class ScopeOptionsModule(scopeOptions: ScopeOptions) : Module() {
    init {
        bind(ScopeOptions::class.java).toInstance(scopeOptions)
    }
}
