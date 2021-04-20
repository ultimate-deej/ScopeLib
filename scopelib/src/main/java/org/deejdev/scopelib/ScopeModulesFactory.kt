package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import toothpick.Scope
import toothpick.config.Module

abstract class ScopeModulesFactory : Parcelable {
    abstract fun createModules(scope: Scope): Array<Module>

    @Parcelize
    object Empty : ScopeModulesFactory() {
        override fun createModules(scope: Scope) = emptyArray<Module>()

        override fun toString() = "Empty"
    }

    @Parcelize
    object ExtendedFragmentLifecycleOnly : ScopeModulesFactory() {
        override fun createModules(scope: Scope): Array<Module> = arrayOf(
            ExtendedFragmentLifecycleModule()
        )

        override fun toString() = "ExtendedFragmentLifecycleOnly"
    }
}
