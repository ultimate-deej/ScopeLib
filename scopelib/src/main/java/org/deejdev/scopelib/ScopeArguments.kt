package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import toothpick.config.Module

abstract class ScopeArguments : Parcelable {
    open fun createModules(): Array<Module> = emptyArray()

    @Parcelize
    object Empty : ScopeArguments() {
        override fun toString() = "Empty"
    }

    @Parcelize
    object LogicalFragmentLifecycleOnly : ScopeArguments() {
        override fun createModules(): Array<Module> = arrayOf(
            LogicalFragmentLifecycleModule()
        )

        override fun toString() = "LogicalFragmentLifecycleOnly"
    }
}
