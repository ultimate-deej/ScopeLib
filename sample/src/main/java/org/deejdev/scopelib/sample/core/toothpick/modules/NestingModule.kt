package org.deejdev.scopelib.sample.core.toothpick.modules

import org.deejdev.scopelib.sample.core.toothpick.BoxedInt
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.NestingLevel
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.NestingParam
import toothpick.config.Module

class NestingModule(level: Int, param: String) : Module() {
    init {
        bind(BoxedInt::class.java).withName(NestingLevel::class.java).toInstance(level as BoxedInt)
        bind(String::class.java).withName(NestingParam::class.java).toInstance(param)
    }
}
