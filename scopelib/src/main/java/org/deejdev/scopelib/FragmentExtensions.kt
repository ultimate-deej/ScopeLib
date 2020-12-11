package org.deejdev.scopelib

import androidx.fragment.app.Fragment
import org.deejdev.scopelib.internal.ensureArguments
import org.deejdev.scopelib.internal.scopeBlueprint

inline fun <reified Name : Annotation, reified ParentName : Annotation> Fragment.attachScopeBlueprint(modulesFactory: ScopeModulesFactory, alsoUse: Boolean = true) =
    attachScopeBlueprint(ScopeBlueprint<Name, ParentName>(modulesFactory))

fun Fragment.attachScopeBlueprint(scopeBlueprint: ScopeBlueprint, alsoUse: Boolean = true) {
    ensureArguments().attachScopeBlueprint(scopeBlueprint, alsoUse)
}

val Fragment.attachedScopeBlueprint: ScopeBlueprint?
    get() = arguments?.scopeBlueprint

fun Fragment.useScope(name: Any?) {
    ensureArguments().useScope(name)
}

inline fun <reified Name : Annotation> Fragment.useScope() = useScope(Name::class.java)
