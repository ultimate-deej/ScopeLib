package org.deejdev.scopelib.lifecycle

import androidx.collection.SimpleArrayMap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import org.deejdev.scopelib.internal.ensureUniqueInstanceId
import org.deejdev.scopelib.internal.isDropping
import java.lang.ref.WeakReference

private val lifecycleOwnerByUniqueId = SimpleArrayMap<String, LogicalFragmentLifecycleTracker>()

internal fun getLogicalFragmentLifecycleTracker(uniqueId: String) = lifecycleOwnerByUniqueId[uniqueId]

private fun Fragment.ensureEntry(): LogicalFragmentLifecycleTracker {
    val uniqueId = ensureUniqueInstanceId()
    if (!lifecycleOwnerByUniqueId.containsKey(uniqueId)) {
        lifecycleOwnerByUniqueId.put(uniqueId, LogicalFragmentLifecycleTracker(uniqueId))
    }
    return lifecycleOwnerByUniqueId[uniqueId]!!
}

class LogicalFragmentLifecycleTracker(private val key: String) : LifecycleEventObserver, LifecycleOwner {
    internal var fragment: WeakReference<Fragment>? = null
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            if (fragment?.get()?.isDropping != false) {
                lifecycleRegistry.handleLifecycleEvent(event)
                lifecycleOwnerByUniqueId.remove(key)
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
            val lifecycleTracker = fragment.ensureEntry()
            lifecycleTracker.updateFragment(fragment)
            fragment.lifecycle.addObserver(lifecycleTracker)
            return lifecycleTracker.lifecycle
        }
    }
}
