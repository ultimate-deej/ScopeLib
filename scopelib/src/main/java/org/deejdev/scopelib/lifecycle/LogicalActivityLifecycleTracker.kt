package org.deejdev.scopelib.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.collection.SimpleArrayMap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import java.util.*

private val lifecycleOwnerByUniqueId = SimpleArrayMap<String, LogicalActivityLifecycleOwner>()

private class LogicalActivityLifecycleOwner : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    override fun getLifecycle(): Lifecycle = lifecycleRegistry
    fun handleLifecycleEvent(event: Lifecycle.Event) = lifecycleRegistry.handleLifecycleEvent(event)
}

class LogicalActivityLifecycleTracker private constructor(activity: ComponentActivity, savedInstanceState: Bundle?) : LifecycleEventObserver, Application.ActivityLifecycleCallbacks {
    private val uniqueId: String

    init {
        uniqueId = savedInstanceState?.getString(KEY_UNIQUE_ID)
            ?: UUID.randomUUID().toString()
        ensureEntryExists()
        activity.application.registerActivityLifecycleCallbacks(this)
        activity.lifecycle.addObserver(this)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        outState.putString(KEY_UNIQUE_ID, uniqueId)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activity.application.unregisterActivityLifecycleCallbacks(this)

        if (activity.isFinishing) {
            lifecycleOwnerByUniqueId[uniqueId]?.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            lifecycleOwnerByUniqueId.remove(uniqueId)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event != Lifecycle.Event.ON_DESTROY) {
            lifecycleOwnerByUniqueId[uniqueId]?.handleLifecycleEvent(event)
        }
    }

    private fun ensureEntryExists() {
        if (!lifecycleOwnerByUniqueId.containsKey(uniqueId)) {
            lifecycleOwnerByUniqueId.put(uniqueId, LogicalActivityLifecycleOwner())
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit
    override fun onActivityStarted(activity: Activity) = Unit
    override fun onActivityResumed(activity: Activity) = Unit
    override fun onActivityPaused(activity: Activity) = Unit
    override fun onActivityStopped(activity: Activity) = Unit

    companion object {
        private const val KEY_UNIQUE_ID = "org.deejdev.scopelib.lifecycle.Activity.UNIQUE_ID"

        @JvmStatic
        fun register(activity: ComponentActivity, savedInstanceState: Bundle?): Lifecycle {
            val tracker = LogicalActivityLifecycleTracker(activity, savedInstanceState)
            activity.lifecycle.addObserver(tracker)
            return lifecycleOwnerByUniqueId[tracker.uniqueId]!!.lifecycle
        }
    }
}
