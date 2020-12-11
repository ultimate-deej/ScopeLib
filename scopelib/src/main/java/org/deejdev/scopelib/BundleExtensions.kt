package org.deejdev.scopelib

import android.os.Bundle
import org.deejdev.scopelib.internal.ensureUniqueInstanceId
import org.deejdev.scopelib.internal.scopeBlueprint
import org.deejdev.scopelib.internal.usedScopeName

inline fun <reified Name : Annotation, reified ParentName : Annotation> Bundle.attachScopeBlueprint(modulesFactory: ScopeModulesFactory, alsoUse: Boolean = true) =
    attachScopeBlueprint(ScopeBlueprint<Name, ParentName>(modulesFactory))

fun Bundle.attachScopeBlueprint(scopeBlueprint: ScopeBlueprint, alsoUse: Boolean = true) = apply {
    val copy = scopeBlueprint.copy()
    copy.instanceId = ensureUniqueInstanceId()
    this.scopeBlueprint = copy

    if (alsoUse) {
        usedScopeName = scopeBlueprint.name
    }
}

val Bundle.attachedScopeBlueprint: ScopeBlueprint?
    get() = scopeBlueprint

fun Bundle.useScope(name: Any?) = apply {
    usedScopeName = name
}

inline fun <reified Name : Annotation> Bundle.useScope() = useScope(Name::class.java)
