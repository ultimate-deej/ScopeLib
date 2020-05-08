package deej.thoroughtestapp.core.toothpick.modules

import deej.thoroughtestapp.core.toothpick.qualifiers.TabsParam
import toothpick.config.Module

class TabsModule(param: String) : Module() {
    init {
        bind(String::class.java).withName(TabsParam::class.java).toInstance(param)
    }
}
