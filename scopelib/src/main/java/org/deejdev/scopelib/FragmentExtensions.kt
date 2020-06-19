package org.deejdev.scopelib

import androidx.fragment.app.Fragment
import org.deejdev.scopelib.internal.ensureArguments
import org.deejdev.scopelib.internal.scopeOptions

fun Fragment.attachScopeOptions(scopeOptions: ScopeOptions, alsoUse: Boolean = true) {
    ensureArguments().attachScopeOptions(scopeOptions, alsoUse)
}

val Fragment.attachedScopeOptions: ScopeOptions?
    get() = arguments?.scopeOptions

fun Fragment.useScope(name: Any?) {
    ensureArguments().useScope(name)
}

inline fun <reified Name : Annotation> Fragment.useScope() = useScope(Name::class.java)
