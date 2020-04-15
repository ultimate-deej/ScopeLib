package deej.scopelib.core.toothpick.scope

interface OpensScope : UsesScope {
    val scopeOptions: ScopeOptions
    override val usedScopeName get() = scopeOptions.name
}
