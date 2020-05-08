package org.deejdev.scopelib.sample.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import org.deejdev.scopelib.ScopeOptionsManager
import org.deejdev.scopelib.ScopeOptionsManagerCallbacks
import org.deejdev.scopelib.ScopeOptionsManagerModule
import org.deejdev.scopelib.sample.R
import org.deejdev.scopelib.sample.core.toothpick.modules.NavigationModule
import org.deejdev.scopelib.sample.core.toothpick.qualifiers.ActivityNavigation
import org.deejdev.scopelib.sample.core.toothpick.scope.RootScope
import org.deejdev.scopelib.sample.presentation.navigation.coordinators.RootCoordinator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Scope
import toothpick.ktp.KTP
import toothpick.smoothie.module.SmoothieApplicationModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var scopeOptionsManager: ScopeOptionsManager

    @Inject lateinit var coordinator: RootCoordinator

    @Inject lateinit var navigatorHolder: NavigatorHolder
    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.content) {
        override fun setupFragmentTransaction(command: Command, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction) {
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
        initScope().inject(this)
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

    private fun initScope(): Scope {
        return KTP.openScope(RootScope::class.java, Scope.ScopeConfig {
            it.installModules(
                ScopeOptionsManagerModule(scopeOptionsManager),
                SmoothieApplicationModule(application),
                NavigationModule<ActivityNavigation>(isDefault = true)
            )
        })
    }

    private fun initScopeOptionsManager(savedInstanceState: Bundle?) {
        scopeOptionsManager = if (savedInstanceState == null)
            ScopeOptionsManager()
        else
            savedInstanceState.getParcelable(ScopeOptionsManager::class.java.name)!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(ScopeOptionsManager::class.java.name, scopeOptionsManager)
    }
}
