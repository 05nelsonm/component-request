package io.matthewnelson.component.request.feature.util

import kotlin.test.Test
import kotlin.test.assertEquals

class RandomIdUnitTest {

    @Test
    fun givenManyRandomIds_whenCompared_areAllUnique() {
        val expected = 1_000
        val list: MutableSet<RandomId> = LinkedHashSet(expected)
        repeat(expected) {
            list.add(RandomId())
        }

        assertEquals(expected, list.size)
    }
}
