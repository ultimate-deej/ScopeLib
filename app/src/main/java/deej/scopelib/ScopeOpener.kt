package deej.scopelib

//class ScopeOpener<N, A : ScopeArguments<N>>(
//    private val scopeController: ScopeController<N, A>
//) {
//    fun ensureOpen(arguments: A) {
//        val current = scopeController.retrieveArguments(arguments.name)
//        if (arguments != current) {
//            if (current != null) {
//                scopeController.close(current.name)
//            }
//            scopeController.open(arguments)
//        }
//    }
//}
