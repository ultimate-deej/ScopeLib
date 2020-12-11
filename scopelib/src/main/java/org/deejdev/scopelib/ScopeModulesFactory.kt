package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import toothpick.config.Module

abstract class ScopeModulesFactory : Parcelable {
    open fun createModules(): Array<Module> = emptyArray()

    @Parcelize
    object Empty : ScopeModulesFactory() {
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
