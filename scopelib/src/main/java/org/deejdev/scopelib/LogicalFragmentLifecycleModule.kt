package org.deejdev.scopelib

import androidx.lifecycle.Lifecycle
import org.deejdev.scopelib.lifecycle.getLogicalFragmentLifecycleTracker
import toothpick.InjectConstructor
import toothpick.config.Module
import toothpick.ktp.binding.bind
import javax.inject.Provider

class LogicalFragmentLifecycleModule : Module() {
    init {
        bind<Lifecycle>().toProvider(LogicalFragmentLifecycleProvider::class)
    }
}

@InjectConstructor
internal class LogicalFragmentLifecycleProvider(private val scopeOptions: ScopeOptions) : Provider<Lifecycle> {
    override fun get(): Lifecycle {
        val tracker = checkNotNull(getLogicalFragmentLifecycleTracker(scopeOptions.instanceId)) {
            "Requested lifecycle entry is missing for \"$scopeOptions\""
        }
        // Enforcing proper use
        checkNotNull(tracker.fragment?.get()) {
            "Make sure you call `register` before injecting logical lifecycle"
        }
        return tracker.lifecycle
    }
}
