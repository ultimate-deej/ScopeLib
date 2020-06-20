package org.deejdev.scopelib.internal

import org.deejdev.scopelib.ScopeBlueprint
import toothpick.config.Module

internal class ScopeBlueprintModule(scopeBlueprint: ScopeBlueprint) : Module() {
    init {
        bind(ScopeBlueprint::class.java).toInstance(scopeBlueprint)
    }
}
