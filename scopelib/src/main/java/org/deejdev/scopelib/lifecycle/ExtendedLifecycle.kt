package org.deejdev.scopelib.lifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment

object ExtendedLifecycle {
    @JvmStatic
    fun register(activity: ComponentActivity, savedInstanceState: Bundle?) = ExtendedActivityLifecycleTracker.register(activity, savedInstanceState)

    @JvmStatic
    fun register(fragment: Fragment) = ExtendedFragmentLifecycleTracker.register(fragment)
}
