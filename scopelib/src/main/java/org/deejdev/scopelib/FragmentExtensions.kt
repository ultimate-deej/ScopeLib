package org.deejdev.scopelib

import androidx.fragment.app.Fragment
import org.deejdev.scopelib.internal.scopeOptions
import org.deejdev.scopelib.internal.usedScopeName

fun Fragment.attachScopeOptions(scopeOptions: ScopeOptions, alsoUse: Boolean = true) {
    this.scopeOptions = scopeOptions
    if (alsoUse) {
        usedScopeName = scopeOptions.name
    }
}

fun Fragment.useScope(name: Any?) {
    usedScopeName = name
}

inline fun <reified Name : Annotation> Fragment.useScope() = useScope(Name::class.java)
