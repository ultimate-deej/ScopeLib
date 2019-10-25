package deej.scopelib.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import deej.scopelib.R
import deej.scopelib.core.toothpick.modules.NavigationModule
import deej.scopelib.core.toothpick.qualifiers.ActivityNavigation
import deej.scopelib.core.toothpick.scope.*
import deej.scopelib.presentation.navigation.coordinators.RootCoordinator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import toothpick.Scope
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieApplicationModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), OpensScope {
    @Inject lateinit var coordinator: RootCoordinator

    @Inject lateinit var navigatorHolder: NavigatorHolder
    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.content) {
        override fun activityBack() {
            // We're here which means it's nowhere to go back to. In this case minimize.
            if (!moveTaskToBack(false)) {
                // But if we couldn't (which should never be the case right?) just do the standard thing
                super@MainActivity.onBackPressed()
            }
        }
    }

    // Next create another app. Prolly use Cicerone, Toothpick, Butter Knife
    //  - Screens to make (don't worry about scopes at first):
    //    - Root screen with a button
    //    - Next push some tabs; extends the scope
    //    - First tab is just a dumb blank screen
    //    - Second tab is a simple tab that subscopes further
    //    - Third tab is a container which also subscopes
    //      - List
    //      - Details (with another subscope)
    //  - Only then add scope-related code. Logs can be keeped.
    //  - Then add arguments so that scopes are actually do something
    //
    // And only when you shape the idea enough to make it work, extract the code into a library and write tests.
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            scopeOptions = getColdStartScopeOptions()
        } else {
            restoreScopeOptions(savedInstanceState)
        }
        initScope().inject(this)
        println("QWE ACTIVITY ON CREATED SCOPE ${scopeOptions.name}")
        supportFragmentManager.registerFragmentLifecycleCallbacks(InjectorFragmentLifecycleCallbacks(scopeOptions, ::scopeOptions::set), false)

        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentById(R.id.content) == null) {
            coordinator.start("initial")
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()

        navigatorHolder.removeNavigator()
    }

    // Activity has no parent to initialize a scope for it. So it has to do it itself.
    private fun initScope(): Scope {
        val scopeOptions = this.scopeOptions.root

        if (Toothpick.isScopeOpen(scopeOptions.name)) {
            return Toothpick.openScope(scopeOptions.name)
        }

        return Toothpick.openScope(scopeOptions.name)
            .installModules(
                ScopeOptionsModule(scopeOptions),
                SmoothieApplicationModule(application),
                NavigationModule<ActivityNavigation>(isDefault = true)
            )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(ScopeOptions::class.java.name, scopeOptions)
    }

    private fun restoreScopeOptions(savedInstanceState: Bundle) {
        scopeOptions = savedInstanceState.getParcelable(ScopeOptions::class.java.name)!!
    }

    private fun getColdStartScopeOptions() =
        ScopeOptions(MainActivity::class.java.name, ScopeArguments.Empty, instanceId = "Singleton root scope")

    override lateinit var scopeOptions: ScopeOptions
}
