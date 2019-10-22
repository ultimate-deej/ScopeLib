package deej.scopelib.core.platform

import android.app.Application
import deej.scopelib.core.toothpick.modules.NavigationModule
import deej.scopelib.core.toothpick.qualifiers.AppNavigation
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieApplicationModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initToothpick()
    }

    private fun initToothpick() {
        Toothpick.openScope(App::class.java)
            .installModules(
                SmoothieApplicationModule(this),
                NavigationModule<AppNavigation>(isDefault = true)
            )
    }
}
