package com.github.esslerc.nim.strategy

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(value= ["strategy"], havingValue = "misere")
class MisereNimStrategy : NimStrategy {
    override fun calculateMove(rows: List<Int>): Pair<Int, Int> {
        logger.info("misere strategy activated")
        // Calculate the XOR sum of all rows
        val xorSum = rows.reduce { acc, i -> acc.xor(i) }

        // Check if the XOR sum is 0
        // If the XOR sum is 0, it means the server is already in a losing position
        // In this case, the server should just create any non-losing position
        if (xorSum == 0) {
            val nonEmptyRows = rows.filter { it > 0 }
            val rowIdx = nonEmptyRows.indices.random()
            val stonesToRemove = (1..nonEmptyRows[rowIdx]).random()
            return Pair(rowIdx, stonesToRemove)
        }

        // Iterate through the rows and find a position that reduces the XOR sum
        for (i in rows.indices) {
            val xorWithoutRowI = xorSum.xor(rows[i])
            if (xorWithoutRowI < rows[i]) {
                val stonesToRemove = rows[i] - xorWithoutRowI
                return Pair(i, stonesToRemove)
            }
        }

        // If no such position is found, simply remove one stone from the first non-empty row
        val nonEmptyRows = rows.filter { it > 0 }
        val rowIdx = rows.indexOfFirst { it == nonEmptyRows[0] }
        val stonesToRemove = 1
        return Pair(rowIdx, stonesToRemove)
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MisereNimStrategy::class.java)
    }
}