package deej.scopelib.core.toothpick.scope

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import toothpick.Toothpick
import toothpick.ktp.extension.getInstance
import javax.inject.Singleton

@Singleton
@Parcelize
class ScopeOptionsManager(
    private val items: MutableList<ScopeOptions> = mutableListOf()
) : Parcelable {

    fun overwrite(scopeOptions: ScopeOptions) {
        val indexOfExisting = items.indexOfFirst { it.name == scopeOptions.name }
        if (indexOfExisting == -1) {
            items.add(scopeOptions)
        } else {
            val existing = items[indexOfExisting]
            if (existing.instanceId != scopeOptions.instanceId) {
                items.removeAt(indexOfExisting)
                items.add(scopeOptions)
            }
        }
    }

    fun removeAndClose(scopeOptions: ScopeOptions) {
        items.remove(scopeOptions)
        if (isSameAsLive(scopeOptions)) {
            Toothpick.closeScope(scopeOptions.name)
            cleanOrphansOf(scopeOptions.name)
        }
    }

    fun materialize() {
        for (scopeOptions in items) {
            if (!isSameAsLive(scopeOptions)) {
                Toothpick.closeScope(scopeOptions.name)
                check(Toothpick.isScopeOpen(scopeOptions.parentName)) { "Can't open $scopeOptions since its parent isn't open" }
                Toothpick.openScopes(scopeOptions.parentName, scopeOptions.name)
                    .installModules(*scopeOptions.scopeArguments.createModules(), ScopeOptionsModule(scopeOptions))
            }
        }
    }

    private fun isSameAsLive(scopeOptions: ScopeOptions): Boolean {
        if (!Toothpick.isScopeOpen(scopeOptions.name)) return false

        val liveScopeOptions = Toothpick.openScope(scopeOptions.name).getInstance<ScopeOptions>()
        return scopeOptions == liveScopeOptions
    }

    private fun cleanOrphansOf(parentName: Class<out Annotation>) {
        val orphans = items.filter { it.parentName == parentName }
        items.removeAll(orphans)
        orphans.forEach { cleanOrphansOf(it.name) }
    }
}
