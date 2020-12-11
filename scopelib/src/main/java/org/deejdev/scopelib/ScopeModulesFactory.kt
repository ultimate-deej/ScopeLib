package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import toothpick.config.Module

abstract class ScopeModulesFactory : Parcelable {
    abstract fun createModules(): Array<Module>

    @Parcelize
    object Empty : ScopeModulesFactory() {
        override fun createModules() = emptyArray<Module>()

        override fun toString() = "Empty"
    }

    @Parcelize
    object LogicalFragmentLifecycleOnly : ScopeModulesFactory() {
        override fun createModules(): Array<Module> = arrayOf(
            LogicalFragmentLifecycleModule()
        )

        override fun toString() = "LogicalFragmentLifecycleOnly"
    }
}
