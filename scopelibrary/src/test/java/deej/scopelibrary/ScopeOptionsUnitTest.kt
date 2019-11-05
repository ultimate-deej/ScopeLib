package deej.scopelibrary

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import org.junit.Assert.assertSame
import org.junit.Test
import java.util.*

class ScopeOptionsUnitTest {
    @Test
    fun tail() {
        val root = ScopeOptions("Root", ScopeArguments.Empty, null)
        assertSame(root, root.tail)

        val firstDescendant = ScopeOptions("Scope 1", ScopeArguments.Empty, null)
        root.child = firstDescendant
        assertSame(firstDescendant, root.tail)

        val secondDescendant = ScopeOptions("Scope 2", ScopeArguments.Empty, null)
        firstDescendant.child = secondDescendant
        assertSame(secondDescendant, root.tail)
    }

    @Test
    fun appendDescendant() {
        val root = ScopeOptions("Root", ScopeArguments.Empty, null)
        val firstDescendant = ScopeOptions("Scope 1", ScopeArguments.Empty, "Root", storeInParent = true)
        val secondDescendant = ScopeOptions("Scope 2", ScopeArguments.Empty, "Scope 1", storeInParent = true)
        root.appendDescendant(firstDescendant)
        root.appendDescendant(secondDescendant)

        assertSame(firstDescendant, root.child)
        assertSame(secondDescendant, root.child?.child)
    }

    @Test
    fun removeDescendantSelf() {
        val (root, _, secondDescendant) = createSampleScopeOptions()

        root.removeDescendant(root.name, null)
        assertSame(secondDescendant, root.tail)
    }

    @Test
    fun removeFirstDescendant() {
        val (root, firstDescendant, _) = createSampleScopeOptions()

        root.removeDescendant(firstDescendant.name, null)
        assertSame(root, root.tail)
    }

    @Test
    fun removeSecondDescendant() {
        val (root, firstDescendant, secondDescendant) = createSampleScopeOptions()

        root.removeDescendant(secondDescendant.name, null)
        assertSame(firstDescendant, root.tail)
    }

    @Test
    fun removeUnrelatedDescendant() {
        val (root, _, secondDescendant) = createSampleScopeOptions()
        val unrelatedDescendant = ScopeOptions("Unrelated Scope", ScopeArguments.Empty, null, storeInParent = true)

        root.removeDescendant(unrelatedDescendant.name, null)
        assertSame(secondDescendant, root.tail)
    }

    @Test
    fun removeDescendantWithSpecificId() {
        val (root, firstDescendant, secondDescendant) = createSampleScopeOptions()

        root.removeDescendant(secondDescendant.name, secondDescendant.instanceId)
        assertSame(firstDescendant, root.tail)
    }

    @Test
    fun removeDescendantWithDifferentId() {
        val (root, _, secondDescendant) = createSampleScopeOptions()

        root.removeDescendant(secondDescendant.name, UUID.randomUUID().toString())
        assertSame(secondDescendant, root.tail)
    }

    private fun createSampleScopeOptions(): List<ScopeOptions> {
        val root = ScopeOptions("Root", ScopeArguments.Empty, null)
        val firstDescendant = ScopeOptions("Scope 1", ScopeArguments.Empty, "Root", storeInParent = true)
        val secondDescendant = ScopeOptions("Scope 2", ScopeArguments.Empty, "Scope 1", storeInParent = true)
        root.appendDescendant(firstDescendant)
        root.appendDescendant(secondDescendant)
        return listOf(root, firstDescendant, secondDescendant)
    }
}
