package deej.scopelib.core.toothpick.scope

import android.os.Bundle
import androidx.fragment.app.Fragment

interface OpensScopeFragment : OpensScope {
    override var scopeOptions: ScopeOptions
        get() {
            if (this !is Fragment) throwNotFragmentException()
            return arguments?.getParcelable(ARGUMENT_SCOPE_OPTIONS)
                ?: throw IllegalStateException("Scope options are missing. Did you forget to set a value previously?")
        }
        set(value) {
            if (this !is Fragment) throwNotFragmentException()
            if (arguments == null) {
                arguments = Bundle()
            }
            requireArguments().putParcelable(ARGUMENT_SCOPE_OPTIONS, value)
        }
}

private fun Any.throwNotFragmentException(): Nothing =
    throw ClassCastException("OpensScopeFragment is for use with ${Fragment::class.java.name} only but applied to ${this.javaClass.name}.")

private const val ARGUMENT_SCOPE_OPTIONS = "(Fragment: OpensScope).scopeOptions"
