package com.github.esslerc.nim.strategy

interface NimStrategy {
    fun calculateMove(rows: List<Int>): Pair<Int, Int>
}