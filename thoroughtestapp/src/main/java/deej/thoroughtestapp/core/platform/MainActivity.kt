package deej.thoroughtestapp.core.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import deej.scopelib.core.toothpick.scope.*
import deej.thoroughtestapp.R
import deej.thoroughtestapp.core.toothpick.modules.NavigationModule
import deej.thoroughtestapp.core.toothpick.qualifiers.ActivityNavigation
import deej.thoroughtestapp.presentation.navigation.coordinators.RootCoordinator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Scope
import toothpick.Toothpick
import toothpick.ktp.extension.getInstance
import toothpick.smoothie.module.SmoothieApplicationModule
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), OpensScope {
    @Inject lateinit var coordinator: RootCoordinator

    @Inject lateinit var navigatorHolder: NavigatorHolder
    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.content) {
        override fun setupFragmentTransaction(command: Command, currentFragment: Fragment?, nextFragment: Fragment, fragmentTransaction: FragmentTransaction) {
            println("QWE nextFragment ${nextFragment.javaClass.simpleName}")
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

    // TODO: child -> next, tail -> lastOpened

    override fun onCreate(savedInstanceState: Bundle?) {
        initScope(savedInstanceState).inject(this)
        println("QWE ACTIVITY ON CREATED SCOPE ${scopeOptions.instanceId} (${scopeOptions.name})")
        supportFragmentManager.registerFragmentLifecycleCallbacks(InjectorFragmentLifecycleCallbacks(scopeOptions, true), false)

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
    // TODO: What a mess and copy-paste in general
    private fun initScope(savedInstanceState: Bundle?): Scope {
        if (savedInstanceState == null) {
            scopeOptions = getColdStartScopeOptions()
        } else {
            restoreScopeOptions(savedInstanceState)
        }

        if (Toothpick.isScopeOpen(scopeOptions.name)) {
            return Toothpick.openScope(scopeOptions.name)
        }
        ensureScopes(scopeOptions)

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

    private fun ensureScopes(scopeOptions: ScopeOptions) {
        val sameAsLive = isSameAsLive(scopeOptions)
        if (sameAsLive == false) {
            val liveId = Toothpick.openScope(scopeOptions.name).getInstance<ScopeOptions>().instanceId
            println("QWE SCOPE (${scopeOptions.name}) INVALID, CLOSING $liveId")
            Toothpick.closeScope(scopeOptions.name)
            val oldTailName = this.scopeOptions.tail.name
            // BEFORE and AFTER - I'm just trying to understand what's wrong after process death
            println("QWE CHILD BEFORE ${scopeOptions.child}")
            this.scopeOptions.removeDescendant(scopeOptions.name, null)
            println("QWE CHILD AFTER ${scopeOptions.child}")
            if (oldTailName != this.scopeOptions.tail.name) {
//                Debug.waitForDebugger()
                println("QWE TAIL CUT DOWN FROM 4 ($oldTailName) TO (${this.scopeOptions.tail.name})")
            }
        }
        if (sameAsLive == null) {
            println("QWE SCOPE (${scopeOptions.name}) NOT OPEN")
        }
        if (sameAsLive == true) {
            println("QWE SCOPE (${scopeOptions.name}) ALREADY OPEN, VALID, SKIPPING")
        }
        if (sameAsLive == null || sameAsLive == false) {
            initializeScope(scopeOptions)
            if (scopeOptions != this.scopeOptions && scopeOptions.storeInParent) {
                this.scopeOptions.appendDescendant(scopeOptions)
            }
        }

        scopeOptions.child?.let(::ensureScopes)
    }

    private fun isSameAsLive(scopeOptions: ScopeOptions): Boolean? {
        if (!Toothpick.isScopeOpen(scopeOptions.name)) return null

        val liveScopeOptions: ScopeOptions = Toothpick.openScope(scopeOptions.name).getInstance()
        println("QWE IS SAME (${scopeOptions.name}) ${scopeOptions.instanceId} LIVE ${liveScopeOptions.instanceId}")
        return scopeOptions.instanceId == liveScopeOptions.instanceId
    }

    private fun initializeScope(scopeOptions: ScopeOptions): Scope {
        val scope = if (scopeOptions.parentName == null)
            Toothpick.openScopes(scopeOptions.name)
        else
            Toothpick.openScopes(scopeOptions.parentName, scopeOptions.name)
        return scope
            .installModules(*scopeOptions.scopeArguments.createModules(), ScopeOptionsModule(scopeOptions))
            .also {
                println("QWE SCOPE NEWLY OPENED ${scopeOptions.instanceId} (${scopeOptions.name}) parent ($scopeOptions.parentName)")
            }
    }

    private fun getColdStartScopeOptions() =
        ScopeOptions(MainActivity::class.java.name, ScopeArguments.Empty, null, instanceId = "Singleton root scope")

    override lateinit var scopeOptions: ScopeOptions
}
