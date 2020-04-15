package deej.scopelibrary

import deej.scopelib.core.toothpick.scope.ScopeArguments
import deej.scopelib.core.toothpick.scope.ScopeOptions
import org.junit.Assert.assertSame
import org.junit.Test
import java.util.*
import javax.inject.Scope

class ScopeOptionsUnitTest {
    @Scope
    annotation class RootScope

    @Scope
    annotation class Scope1

    @Scope
    annotation class Scope2

    @Scope
    annotation class UnrelatedScope

    @Test
    fun `tail is last next in chain`() {
        val root = ScopeOptions(RootScope::class.java, ScopeArguments.Empty, null)
        assertSame(root, root.tail)

        val firstDescendant = ScopeOptions(Scope1::class.java, ScopeArguments.Empty, null)
        root.next = firstDescendant
        assertSame(firstDescendant, root.tail)

        val secondDescendant = ScopeOptions(Scope2::class.java, ScopeArguments.Empty, null)
        firstDescendant.next = secondDescendant
        assertSame(secondDescendant, root.tail)
    }

    @Test
    fun `appendDescendant sets next of tail`() {
        val root = ScopeOptions(RootScope::class.java, ScopeArguments.Empty, null)
        val firstDescendant = ScopeOptions(Scope1::class.java, ScopeArguments.Empty, RootScope::class.java, managedByParent = true)
        val secondDescendant = ScopeOptions(Scope2::class.java, ScopeArguments.Empty, Scope1::class.java, managedByParent = true)
        root.appendTail(firstDescendant)
        root.appendTail(secondDescendant)

        assertSame(firstDescendant, root.next)
        assertSame(secondDescendant, root.next?.next)
    }

    @Test
    fun `removeStartingFrom(root) doesn't change the chain`() {
        val (root, _, secondDescendant) = createSampleScopeOptions()

        root.removeStartingFrom(root.name, null)
        assertSame(secondDescendant, root.tail)
    }

    @Test
    fun `removeStartingFrom(firstDescendant) leaves only root node`() {
        val (root, firstDescendant, _) = createSampleScopeOptions()

        root.removeStartingFrom(firstDescendant.name, null)
        assertSame(root, root.tail)
    }

    @Test
    fun `removeStartingFrom(secondDescendant) leaves firstDescendant in the chain`() {
        val (root, firstDescendant, secondDescendant) = createSampleScopeOptions()

        root.removeStartingFrom(secondDescendant.name, null)
        assertSame(firstDescendant, root.tail)
    }

    @Test
    fun `removeStartingFrom(nodeNotInChain) doesn't change the chain`() {
        val (root, _, secondDescendant) = createSampleScopeOptions()
        val nodeNotInChain = ScopeOptions(UnrelatedScope::class.java, ScopeArguments.Empty, null, managedByParent = true)

        root.removeStartingFrom(nodeNotInChain.name, null)
        assertSame(secondDescendant, root.tail)
    }

    @Test
    fun `removeStartingFrom with specific instanceId removes the node`() {
        val (root, firstDescendant, secondDescendant) = createSampleScopeOptions()

        root.removeStartingFrom(secondDescendant.name, secondDescendant.instanceId)
        assertSame(firstDescendant, root.tail)
    }

    @Test
    fun `removeStartingFrom with instanceId different from what is in the chain doesn't change the chain`() {
        val (root, _, secondDescendant) = createSampleScopeOptions()

        root.removeStartingFrom(secondDescendant.name, UUID.randomUUID().toString())
        assertSame(secondDescendant, root.tail)
    }

    private fun createSampleScopeOptions(): List<ScopeOptions> {
        val root = ScopeOptions(RootScope::class.java, ScopeArguments.Empty, null)
        val firstDescendant = ScopeOptions(Scope1::class.java, ScopeArguments.Empty, RootScope::class.java, managedByParent = true)
        val secondDescendant = ScopeOptions(Scope2::class.java, ScopeArguments.Empty, Scope1::class.java, managedByParent = true)
        root.appendTail(firstDescendant)
        root.appendTail(secondDescendant)
        return listOf(root, firstDescendant, secondDescendant)
    }
}
