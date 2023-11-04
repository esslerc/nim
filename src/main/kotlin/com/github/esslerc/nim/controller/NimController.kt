package com.github.esslerc.nim.controller

import com.github.esslerc.nim.strategy.NimStrategy
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/nim")
class NimGameController(
    private val nimStrategy: NimStrategy
) {
    private var rows = listOf(3, 5, 7)

    @GetMapping("/reset")
    fun reset() {
        rows = listOf(3, 5, 7)
    }

    @GetMapping("/status")
    fun getStatus(): List<Int> = rows

    @PostMapping("/move")
    fun makeMove(@RequestBody move: Move): String {
        if (move.row < 1 || move.row > 3 || move.stonesToTake <= 0 || move.stonesToTake > rows[move.row - 1]) {
            return "Invalid move"
        }

        rows = rows.mapIndexed { index, value ->
            if (index == move.row - 1) {
                value - move.stonesToTake
            } else {
                value
            }
        }

        val serverMove = nimStrategy.calculateMove(rows)

        rows = rows.mapIndexed { index, value ->
            if (index == serverMove.first) {
                value - serverMove.second
            } else {
                value
            }
        }

        return if (rows.sum() <= 0) {
            """
                Server has won.
                Current status: $rows
            """.trimIndent()
        } else {
            """
                You have removed ${move.stonesToTake} stones from row ${move.row}.
                Server has removed  ${serverMove.second} stones from row ${serverMove.first + 1}.
                Current status: $rows
            """.trimIndent()
        }
    }
}

data class Move (
    val row: Int,
    val stonesToTake: Int
)
