package deej.scopelibrary

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import org.junit.Assert.assertSame
import org.junit.Test
import java.util.*

class ScopeOptionsUnitTest {
    @Test
    fun head() {
        val root = ScopeOptions("Root", ScopeArguments.Empty)
        assertSame(root, root.head)

        val firstExtension = ScopeOptions("Scope 1", ScopeArguments.Empty)
        root.extension = firstExtension
        assertSame(firstExtension, root.head)

        val secondExtension = ScopeOptions("Scope 2", ScopeArguments.Empty)
        firstExtension.extension = secondExtension
        assertSame(secondExtension, root.head)
    }

    @Test
    fun extend() {
        val (root, firstExtension, secondExtension) = createSampleScopeOptions()

        assertSame(firstExtension, root.extension)
        assertSame(secondExtension, root.extension?.extension)
    }

    @Test
    fun removeExtensionSelf() {
        val (root, _, secondExtension) = createSampleScopeOptions()

        root.removeExtension(root.name, null)
        assertSame(secondExtension, root.head)
    }

    @Test
    fun removeFirstExtension() {
        val (root, firstExtension, _) = createSampleScopeOptions()

        root.removeExtension(firstExtension.name, null)
        assertSame(root, root.head)
    }

    @Test
    fun removeSecondExtension() {
        val (root, firstExtension, secondExtension) = createSampleScopeOptions()

        root.removeExtension(secondExtension.name, null)
        assertSame(firstExtension, root.head)
    }

    @Test
    fun removeUnrelatedExtension() {
        val (root, _, secondExtension) = createSampleScopeOptions()
        val unrelatedExtension = ScopeOptions("Unrelated Scope", ScopeArguments.Empty, extends = true)

        root.removeExtension(unrelatedExtension.name, null)
        assertSame(secondExtension, root.head)
    }

    @Test
    fun removeExtensionWithSpecificId() {
        val (root, firstExtension, secondExtension) = createSampleScopeOptions()

        root.removeExtension(secondExtension.name, secondExtension.instanceId)
        assertSame(firstExtension, root.head)
    }

    @Test
    fun removeExtensionWithDifferentId() {
        val (root, _, secondExtension) = createSampleScopeOptions()

        root.removeExtension(secondExtension.name, UUID.randomUUID().toString())
        assertSame(secondExtension, root.head)
    }

    private fun createSampleScopeOptions(): List<ScopeOptions> {
        val root = ScopeOptions("Root", ScopeArguments.Empty)
        val firstExtension = ScopeOptions("Scope 1", ScopeArguments.Empty, extends = true)
        val secondExtension = ScopeOptions("Scope 2", ScopeArguments.Empty, extends = true)
        root.extend(firstExtension)
        root.extend(secondExtension)
        return listOf(root, firstExtension, secondExtension)
    }
}
