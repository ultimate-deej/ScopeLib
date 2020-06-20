package org.deejdev.scopelib

import android.os.Bundle
import org.deejdev.scopelib.internal.ensureUniqueInstanceId
import org.deejdev.scopelib.internal.scopeOptions
import org.deejdev.scopelib.internal.usedScopeName

fun Bundle.attachScopeOptions(scopeOptions: ScopeOptions, alsoUse: Boolean = true) = apply {
    val copy = scopeOptions.copy()
    copy.instanceId = ensureUniqueInstanceId()
    this.scopeOptions = copy

    if (alsoUse) {
        usedScopeName = scopeOptions.name
    }
}

val Bundle.attachedScopeOptions: ScopeOptions?
    get() = scopeOptions

fun Bundle.useScope(name: Any?) = apply {
    usedScopeName = name
}

inline fun <reified Name : Annotation> Bundle.useScope() = useScope(Name::class.java)
