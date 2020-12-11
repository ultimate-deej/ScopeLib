package org.deejdev.scopelib

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.deejdev.scopelib.internal.ScopeBlueprintModule
import toothpick.ktp.KTP
import toothpick.ktp.extension.getInstance

@Parcelize
class ScopeBlueprintManager(
    private val items: MutableList<ScopeBlueprint> = mutableListOf()
) : Parcelable {

    fun overwrite(scopeBlueprint: ScopeBlueprint) {
        val indexOfExisting = items.indexOfFirst { it.name == scopeBlueprint.name }
        if (indexOfExisting == -1) {
            items.add(scopeBlueprint)
        } else {
            val existing = items[indexOfExisting]
            if (existing.instanceId != scopeBlueprint.instanceId) {
                items.removeAt(indexOfExisting)
                cleanOrphansOf(scopeBlueprint.name)
                items.add(scopeBlueprint)
            }
        }
    }

    fun removeAndClose(scopeBlueprint: ScopeBlueprint) {
        if (items.remove(scopeBlueprint)) {
            cleanOrphansOf(scopeBlueprint.name)
        }
        if (isSameAsMaterialized(scopeBlueprint)) {
            KTP.closeScope(scopeBlueprint.name)
        }
    }

    fun materialize() {
        for (scopeBlueprint in items) {
            check(scopeBlueprint.isAttached) { "$scopeBlueprint must be attached to a Fragment before use" }
            if (!isSameAsMaterialized(scopeBlueprint)) {
                KTP.closeScope(scopeBlueprint.name)
                check(KTP.isScopeOpen(scopeBlueprint.parentName)) { "Can't open $scopeBlueprint since its parent isn't open" }
                KTP.openScopes(scopeBlueprint.parentName, scopeBlueprint.name)
                    .also { scope ->
                        scope.installModules(*scopeBlueprint.modulesFactory.createModules(scope), ScopeBlueprintModule(scopeBlueprint))
                    }
            }
        }
    }

    private fun isSameAsMaterialized(scopeBlueprint: ScopeBlueprint): Boolean {
        if (!KTP.isScopeOpen(scopeBlueprint.name)) return false

        val liveScopeBlueprint = KTP.openScope(scopeBlueprint.name).getInstance<ScopeBlueprint>()
        return scopeBlueprint == liveScopeBlueprint
    }

    private fun cleanOrphansOf(parentName: Any) {
        val orphans = items.filter { it.parentName == parentName }
        items.removeAll(orphans)
        orphans.forEach { cleanOrphansOf(it.name) }
    }
}
