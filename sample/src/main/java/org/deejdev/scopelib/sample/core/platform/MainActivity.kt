package org.deejdev.scopelib.sample.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import org.deejdev.scopelib.ScopeBlueprintManager
import org.deejdev.scopelib.ScopeBlueprintManagerCallbacks
import org.deejdev.scopelib.ScopeBlueprintManagerModule
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
    private lateinit var scopeBlueprintManager: ScopeBlueprintManager

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
        initScopeBlueprintManager(savedInstanceState)
        initScope().inject(this)
        supportFragmentManager.registerFragmentLifecycleCallbacks(ScopeBlueprintManagerCallbacks(scopeBlueprintManager), true)

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
                ScopeBlueprintManagerModule(scopeBlueprintManager),
                SmoothieApplicationModule(application),
                NavigationModule<ActivityNavigation>(isDefault = true)
            )
        })
    }

    private fun initScopeBlueprintManager(savedInstanceState: Bundle?) {
        scopeBlueprintManager = if (savedInstanceState == null)
            ScopeBlueprintManager()
        else
            savedInstanceState.getParcelable(ScopeBlueprintManager::class.java.name)!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(ScopeBlueprintManager::class.java.name, scopeBlueprintManager)
    }
}
