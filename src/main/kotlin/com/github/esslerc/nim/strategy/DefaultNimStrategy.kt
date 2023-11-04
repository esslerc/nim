package com.github.esslerc.nim.strategy

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.lang.IllegalStateException

@Component
@ConditionalOnProperty(value= ["strategy"], havingValue = "default")
class DefaultNimStrategy : NimStrategy {
    override fun calculateMove(rows: List<Int>): Pair<Int, Int> {
        logger.info("default strategy activated")

        val nimSum = rows.reduce { acc, i -> acc.xor(i) }
        val targetRow = rows.indexOfFirst { it.xor(nimSum) < it }
        val stonesToRemove = rows[targetRow] - (rows[targetRow].xor(nimSum))
        return Pair(targetRow, stonesToRemove)

    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(DefaultNimStrategy::class.java)
    }
}