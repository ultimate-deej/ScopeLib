package deej.scopelib.presentation.navigation.nesting

/**
 * When applied to a child, signifies that the parent should delegate back presses to the child.
 *
 * Within the container hierarchy (app-wise) this interface should ONLY be used to forward back presses from parents to children.
 * Should not be used by by children to access their parents.
 * Using the interface to refer to itself is permitted (e.g. if there are other elements on the screen that lead back).
 */
interface OnBackPressedHandler {
    fun onBackPressed()
}
