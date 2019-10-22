package deej.scopelib.presentation.navigation.nesting

import androidx.fragment.app.Fragment

/**
 * Signifies that a Fragment or Activity can contain a stack of child fragments.
 *
 * Within the container hierarchy (app-wise) this interface should ONLY be used by children to access their parents.
 * Should not be used by an object to refer to itself.
 *
 * All containers in a hierarchy must implement this interface in order for the interface to work correctly.
 */
interface NavigationContainer {
    val navigationContainerCanPop: Boolean
    fun navigationContainerPop()
}

val Fragment.parentNavigationContainer: NavigationContainer?
    get() {
        var parent: Fragment? = parentFragment
        while (parent != null) {
            if (parent is NavigationContainer) {
                return parent
            }
            parent = parent.parentFragment
        }
        return activity as? NavigationContainer
    }

/** Helps decide which action to show in Toolbar. */
val Fragment.anyAncestorNavigationContainerCanPop: Boolean
    get() {
        val parent = parentNavigationContainer ?: return false
        return when {
            parent.navigationContainerCanPop -> true
            parent is Fragment -> parent.anyAncestorNavigationContainerCanPop
            else -> false
        }
    }
