package deej.scopelib

import toothpick.config.Module

class AndroidToothpickScopeArgumentsModule(arguments: AndroidToothpickScopeArguments) : Module() {
    init {
        bind(AndroidToothpickScopeArguments::class.java).toInstance(arguments)
    }
}
