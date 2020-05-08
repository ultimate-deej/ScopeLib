package org.deejdev.scopelib.sample.core.toothpick.modules

import org.deejdev.scopelib.sample.core.toothpick.qualifiers.TabsParam
import toothpick.config.Module

class TabsModule(param: String) : Module() {
    init {
        bind(String::class.java).withName(TabsParam::class.java).toInstance(param)
    }
}
