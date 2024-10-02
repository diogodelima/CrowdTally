package com.example.crowdtally

import com.example.crowdtally.model.Counter
import org.junit.Test

import org.junit.Assert.*

class CounterTests {

    @Test
    fun `increment the counter value`() {

        var counter = Counter(current = 5)

        assertEquals(Counter(current = 6), ++counter)
    }

    @Test
    fun `decrement the counter value`() {

        var counter = Counter(current = 5)

        assertEquals(Counter(current = 4), --counter)
    }

    @Test
    fun `increase the counter value to a number greater than capacity`() {

        var counter = Counter(current = 20)

        assertEquals(Counter(current = 20), ++counter)
    }

    @Test
    fun `decrease the counter value to a number less than 0`() {

        var counter = Counter(current = 0)

        assertEquals(Counter(current = 0), --counter)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `initialize the counter with a value less than 0`() {
        Counter(current = -5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `initialize the counter with a value greater than capacity`() {
        Counter(current = 30)
    }

}