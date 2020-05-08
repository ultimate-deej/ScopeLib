package org.deejdev.scopelib.sample.core.toothpick.modules

import org.deejdev.scopelib.sample.core.toothpick.qualifiers.SimpleScopedTabParam
import toothpick.config.Module

class SimpleScopedTabModule(param: Int) : Module() {
    init {
        bind(String::class.java).withName(SimpleScopedTabParam::class.java).toInstance("Simple value from scope $param")
    }
}
