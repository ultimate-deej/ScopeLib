package deej.scopelib

import deej.scopelib.core.toothpick.scope.ScopeArguments
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class ScopeOpenerUnitTest {
    data class ParameterlessScopeArguments(override val name: String) : ScopeArguments
    data class TestScopeArguments(override val name: String, val parameter: Int) : ScopeArguments

    @Test
    fun scopeArgumentsEquality() {
        val scopeArguments1 = ParameterlessScopeArguments("A parameterless scope")
        val scopeArguments2 = ParameterlessScopeArguments("A parameterless scope")
        assertEquals(scopeArguments1, scopeArguments2)
    }

    @Test
    fun openSame() {
        val scopeController = TestScopeController()
        val scopeOpener = ScopeOpener(scopeController)

        val scopeArguments = TestScopeArguments("A parameterless scope", 1)
        scopeOpener.ensureOpen(scopeArguments)

        scopeOpener.ensureOpen(scopeArguments.copy())

        assertEquals(1, scopeController.openCount)
        assertEquals(0, scopeController.closeCount)
        assertSame(scopeArguments, scopeController.retrieveArguments(scopeArguments.name))
    }

    @Test
    fun openDifferent() {
        val scopeName = "Test scope"
        val scopeController = TestScopeController()
        val scopeOpener = ScopeOpener(scopeController)

        val scopeArguments1 = TestScopeArguments(scopeName, 1)
        scopeOpener.ensureOpen(scopeArguments1)

        val scopeArguments2 = TestScopeArguments(scopeName, 2)
        scopeOpener.ensureOpen(scopeArguments2)

        assertEquals(2, scopeController.openCount)
        assertEquals(1, scopeController.closeCount)
        assertEquals(scopeArguments2, scopeController.retrieveArguments(scopeName))
    }
}

class TestScopeController : ScopeController<ScopeArguments> {
    private var currentArguments: ScopeArguments? = null

    var openCount: Int = 0
        private set

    var closeCount: Int = 0
        private set

    override fun open(arguments: ScopeArguments) {
        currentArguments = arguments
        openCount++
    }

    override fun close(name: Any) {
        currentArguments = null
        closeCount++
    }

    override fun retrieveArguments(name: Any) = currentArguments.takeIf { it?.name == name }
}
