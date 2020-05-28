package org.deejdev.scopelib.lifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment

object LogicalLifecycle {
    @JvmStatic
    fun register(activity: ComponentActivity, savedInstanceState: Bundle?) = LogicalActivityLifecycleTracker.register(activity, savedInstanceState)

    @JvmStatic
    fun register(fragment: Fragment) = LogicalFragmentLifecycleTracker.register(fragment)
}
