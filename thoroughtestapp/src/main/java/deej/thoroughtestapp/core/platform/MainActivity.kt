package deej.thoroughtestapp.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import deej.scopelib.core.toothpick.scope.*
import deej.thoroughtestapp.R
import deej.thoroughtestapp.core.toothpick.modules.NavigationModule
import deej.thoroughtestapp.core.toothpick.qualifiers.ActivityNavigation
import deej.thoroughtestapp.core.toothpick.scope.RootScope
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Scope
import toothpick.Toothpick
import toothpick.smoothie.module.SmoothieApplicationModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var scopeOptionsManager: ScopeOptionsManager

    @Inject lateinit var coordinator: RootCoordinator

    @Inject lateinit var navigatorHolder: NavigatorHolder
    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.content) {
        override fun setupFragmentTransaction(command: Command, currentFragment: Fragment?, nextFragment: Fragment, fragmentTransaction: FragmentTransaction) {
            fragmentTransaction.setReorderingAllowed(true)
        }

        override fun activityBack() {
            // We're here which means it's nowhere to go back to. In this case minimize.
            if (!moveTaskToBack(false)) {
                // But if we couldn't (which should never be the case right?) just do the standard thing
                super@MainActivity.onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initScopeOptionsManager(savedInstanceState)
        initScope(savedInstanceState).inject(this)
        supportFragmentManager.registerFragmentLifecycleCallbacks(ScopeOptionsManagerCallbacks(scopeOptionsManager), true)

        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentById(R.id.content) == null) {
            coordinator.home()
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
    private fun initScope(savedInstanceState: Bundle?): Scope {
        if (savedInstanceState == null) {
            initColdStartScopeOptions()
        } else {
            restoreScopeOptions(savedInstanceState)
        }

        return Toothpick.openScope(scopeOptions.name)
            .installModules(
                ScopeOptionsManagerModule(scopeOptionsManager),
                ScopeOptionsModule(scopeOptions),
                SmoothieApplicationModule(application),
                NavigationModule<ActivityNavigation>(isDefault = true)
            )
    }

    private fun initScopeOptionsManager(savedInstanceState: Bundle?) {
        scopeOptionsManager = if (savedInstanceState == null)
            ScopeOptionsManager()
        else
            savedInstanceState.getParcelable(ScopeOptionsManager::class.java.name)!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(ScopeOptions::class.java.name, scopeOptions)
        outState.putParcelable(ScopeOptionsManager::class.java.name, scopeOptionsManager)
    }

    private fun initColdStartScopeOptions() {
        scopeOptions = ScopeOptions(RootScope::class.java, ScopeArguments.Empty, null, instanceId = "Singleton root scope")
    }

    private fun restoreScopeOptions(savedInstanceState: Bundle) {
        scopeOptions = savedInstanceState.getParcelable(ScopeOptions::class.java.name)!!
    }

    lateinit var scopeOptions: ScopeOptions
}
