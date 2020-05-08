package deej.thoroughtestapp.core.toothpick.modules

import deej.thoroughtestapp.core.toothpick.BoxedInt
import deej.thoroughtestapp.core.toothpick.qualifiers.NestingLevel
import deej.thoroughtestapp.core.toothpick.qualifiers.NestingParam
import toothpick.config.Module

class NestingModule(level: Int, param: String) : Module() {
    init {
        bind(BoxedInt::class.java).withName(NestingLevel::class.java).toInstance(level as BoxedInt)
        bind(String::class.java).withName(NestingParam::class.java).toInstance(param)
    }
}
