package deej.thoroughtestapp.core.toothpick.modules

import deej.thoroughtestapp.core.toothpick.qualifiers.SimpleScopedTabParam
import toothpick.config.Module
import kotlin.random.Random
import kotlin.random.nextInt

class SimpleScopedTabModule : Module() {
    init {
        bind(String::class.java).withName(SimpleScopedTabParam::class.java).toInstance("Simple value from scope ${Random.nextInt(0..50)}")
    }
}
