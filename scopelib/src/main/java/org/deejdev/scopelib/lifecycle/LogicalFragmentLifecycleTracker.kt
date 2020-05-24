package org.deejdev.scopelib.lifecycle

import android.os.Binder
import android.os.Bundle
import androidx.core.app.BundleCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.deejdev.scopelib.internal.isDropping
import java.lang.ref.WeakReference

private const val KEY_LIFECYCLE_TRACKER = "org.deejdev.scopelib.lifecycle.Fragment.LIFECYCLE_TRACKER"

val Fragment.logicalLifecycleOwner: LifecycleOwner
    get() {
        if (arguments == null) arguments = Bundle()

        val arguments = requireArguments()

        val existingWrapped = BundleCompat.getBinder(arguments, KEY_LIFECYCLE_TRACKER) as? LifecycleTrackerReference
        val tracker = when (val existingTracker = existingWrapped?.value) {
            null -> LogicalFragmentLifecycleTracker().also { newTracker ->
                BundleCompat.putBinder(arguments, KEY_LIFECYCLE_TRACKER, LifecycleTrackerReference(newTracker))
            }
            else -> existingTracker
        }
        tracker.updateFragment(this)
        lifecycle.addObserver(tracker)
        return tracker
    }

private class LogicalFragmentLifecycleTracker : LifecycleEventObserver, LifecycleOwner {
    private var fragment: WeakReference<Fragment>? = null
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event != Lifecycle.Event.ON_DESTROY || fragment?.get()?.isDropping != false) {
            lifecycleRegistry.handleLifecycleEvent(event)
        }
    }

    fun updateFragment(newFragment: Fragment) {
        fragment = WeakReference(newFragment)
    }
}

private class LifecycleTrackerReference(val value: LogicalFragmentLifecycleTracker) : Binder()
