package dev.mbo.gitlabext

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ByLazyTest {

    @Test
    fun test() {
        val instanceA = A("a")
        val instanceB = B("b")

        assertThat(instanceA.testStr).isEqualTo("testStrGenA")
        assertThat(instanceA.value1).isEqualTo("a")
        assertThat(instanceB.testStr).isEqualTo("testStrGenB")
        assertThat(instanceB.value1).isEqualTo("b")
    }

    open class A constructor(
        val value1: String
    ) {
        val testStr: String by lazy { testStrGen() }

        open fun testStrGen(): String {
            return "testStrGenA"
        }
    }

    open class B constructor(
        value1: String
    ) : A(value1) {

        override fun testStrGen(): String {
            return "testStrGenB"
        }

    }
}