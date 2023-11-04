package com.github.esslerc.nim.strategy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultNimStrategyTests {

    private lateinit var strategy: DefaultNimStrategy

    @BeforeEach
    fun setUp() {
        strategy = DefaultNimStrategy()
    }

    @Test
    fun `calculateMove should return valid move for given rows`() {
        val rows = listOf(3, 5, 7)
        val move = strategy.calculateMove(rows)
        assertEquals(0, move.first) // Removing stones from the third row
        assertEquals(1, move.second) // Removing 1 stone
    }
}
