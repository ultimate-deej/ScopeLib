package org.deejdev.scopelib

import androidx.lifecycle.Lifecycle
import org.deejdev.scopelib.lifecycle.ensureExtendedFragmentLifecycleTracker
import toothpick.InjectConstructor
import toothpick.config.Module
import javax.inject.Provider
import kotlin.reflect.KClass

class ExtendedFragmentLifecycleModule(name: Class<out Annotation>? = null) : Module() {
    constructor(name: KClass<out Annotation>) : this(name.java)

    init {
        if (name == null) {
            bind(Lifecycle::class.java).toProvider(ExtendedFragmentLifecycleProvider::class.java)
        } else {
            bind(Lifecycle::class.java).withName(name).toProvider(ExtendedFragmentLifecycleProvider::class.java)
        }
    }

    companion object {
        inline operator fun <reified Name : Annotation> invoke() =
            ExtendedFragmentLifecycleModule(Name::class.java)
    }
}

@InjectConstructor
internal class ExtendedFragmentLifecycleProvider(private val scopeBlueprint: ScopeBlueprint) : Provider<Lifecycle> {
    override fun get(): Lifecycle {
        val tracker = ensureExtendedFragmentLifecycleTracker(scopeBlueprint.instanceId)
        return tracker.lifecycle
    }
}
