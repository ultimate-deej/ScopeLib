package deej.thoroughtestapp.core.toothpick.modules

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class NavigationModule<A : Annotation>(name: Class<A>, isDefault: Boolean = false) : Module() {
    init {
        val cicerone = Cicerone.create()
        bind(NavigatorHolder::class.java).withName(name).toInstance(cicerone.navigatorHolder)
        bind(Router::class.java).withName(name).toInstance(cicerone.router)

        if (isDefault) {
            bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)
            bind(Router::class.java).toInstance(cicerone.router)
        }
    }

    companion object {
        inline operator fun <reified A : Annotation> invoke(isDefault: Boolean) = NavigationModule(A::class.java, isDefault)
    }
}
