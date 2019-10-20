package deej.scopelib

interface ScopeController<A : ScopeArguments> {
    fun open(arguments: A)
    fun close(name: Any)
    fun retrieveArguments(name: Any): A?
}
