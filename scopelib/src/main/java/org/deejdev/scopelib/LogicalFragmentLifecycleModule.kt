package org.deejdev.scopelib

import androidx.lifecycle.Lifecycle
import org.deejdev.scopelib.lifecycle.ensureLogicalFragmentLifecycleTracker
import toothpick.InjectConstructor
import toothpick.config.Module
import javax.inject.Provider
import kotlin.reflect.KClass

class LogicalFragmentLifecycleModule(name: Class<out Annotation>? = null) : Module() {
    constructor(name: KClass<out Annotation>) : this(name.java)

    init {
        if (name == null) {
            bind(Lifecycle::class.java).toProvider(LogicalFragmentLifecycleProvider::class.java)
        } else {
            bind(Lifecycle::class.java).withName(name).toProvider(LogicalFragmentLifecycleProvider::class.java)
        }
    }

    companion object {
        inline operator fun <reified Name : Annotation> invoke() =
            LogicalFragmentLifecycleModule(Name::class.java)
    }
}

@InjectConstructor
internal class LogicalFragmentLifecycleProvider(private val scopeBlueprint: ScopeBlueprint) : Provider<Lifecycle> {
    override fun get(): Lifecycle {
        val tracker = ensureLogicalFragmentLifecycleTracker(scopeBlueprint.instanceId)
        return tracker.lifecycle
    }
}
