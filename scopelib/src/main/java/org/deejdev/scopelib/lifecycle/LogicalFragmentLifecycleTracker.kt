package org.deejdev.scopelib.lifecycle

import androidx.collection.SimpleArrayMap
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import org.deejdev.scopelib.internal.ensureUniqueInstanceId
import org.deejdev.scopelib.internal.isDropping
import java.lang.ref.WeakReference

private val lifecycleOwnerByUniqueId = SimpleArrayMap<String, LogicalFragmentLifecycleTracker>()

internal fun ensureLogicalFragmentLifecycleTracker(uniqueId: String): LogicalFragmentLifecycleTracker {
    if (!lifecycleOwnerByUniqueId.containsKey(uniqueId)) {
        lifecycleOwnerByUniqueId.put(uniqueId, LogicalFragmentLifecycleTracker(uniqueId))
    }
    return lifecycleOwnerByUniqueId[uniqueId]!!
}

class LogicalFragmentLifecycleTracker(private val key: String) : LifecycleEventObserver, LifecycleOwner {
    internal var fragment: WeakReference<Fragment>? = null
    private val lifecycleRegistry = object : LifecycleRegistry(this) {
        override fun addObserver(observer: LifecycleObserver) {
            // Enforcing proper use
            checkNotNull(fragment?.get()) {
                "Make sure you call `register` with the current Fragment instance before using this lifecycle"
            }
            checkNotNull(fragment?.get())
            super.addObserver(observer)
        }
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            if (fragment?.get()?.isDropping != false) {
                lifecycleRegistry.handleLifecycleEvent(event)
                lifecycleOwnerByUniqueId.remove(key)
                fragment = null // This will re-enable the check in `addObserver` without waiting for garbage collection
            }
        } else {
            lifecycleRegistry.handleLifecycleEvent(event)
        }
    }

    internal fun updateFragment(newFragment: Fragment) {
        fragment = WeakReference(newFragment)
    }

    companion object {
        @JvmStatic
        fun register(fragment: Fragment): Lifecycle {
            val lifecycleTracker = ensureLogicalFragmentLifecycleTracker(fragment.ensureUniqueInstanceId())
            lifecycleTracker.updateFragment(fragment)
            fragment.lifecycle.addObserver(lifecycleTracker)
            return lifecycleTracker.lifecycle
        }
    }
}
