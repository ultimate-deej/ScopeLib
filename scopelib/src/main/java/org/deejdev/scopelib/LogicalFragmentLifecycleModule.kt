package org.deejdev.scopelib

import androidx.lifecycle.Lifecycle
import org.deejdev.scopelib.lifecycle.ensureLogicalFragmentLifecycleTracker
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
internal class LogicalFragmentLifecycleProvider(private val scopeBlueprint: ScopeBlueprint) : Provider<Lifecycle> {
    override fun get(): Lifecycle {
        val tracker = ensureLogicalFragmentLifecycleTracker(scopeBlueprint.instanceId)
        return tracker.lifecycle
    }
}
