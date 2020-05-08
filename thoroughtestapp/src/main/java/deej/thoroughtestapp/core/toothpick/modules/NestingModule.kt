package deej.thoroughtestapp.core.toothpick.modules

import deej.thoroughtestapp.core.toothpick.BoxedInt
import deej.thoroughtestapp.core.toothpick.qualifiers.NestingLevel
import toothpick.config.Module

class NestingModule(level: Int) : Module() {
    init {
        bind(BoxedInt::class.java).withName(NestingLevel::class.java).toInstance(level as BoxedInt)
    }
}
