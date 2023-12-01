package aoc.y2022.d2

import aoc.BaseSolver
import org.junit.jupiter.api.Test

private const val ROCK = 1
private const val PAPER = 2
private const val SCISSORS = 3

private const val LOSS = 0
private const val DRAW = 3
private const val WIN = 6

class Solver : BaseSolver(year = 2022, day = 2) {

    @Test
    fun `Solve part 1`() {
        println(input.sumOf { scores[it.last()]!![it.first()]!! })
    }

    @Test
    fun `Solve part 2`() {
        println(input.sumOf { counterPlays[it.last()]!![it.first()]!! })
    }

    private val scores = mapOf(
        'X' to mapOf(
            'A' to ROCK + DRAW,
            'B' to ROCK + LOSS,
            'C' to ROCK + WIN
        ),
        'Y' to mapOf(
            'A' to PAPER + WIN,
            'B' to PAPER + DRAW,
            'C' to PAPER + LOSS
        ),
        'Z' to mapOf(
            'A' to SCISSORS + LOSS,
            'B' to SCISSORS + WIN,
            'C' to SCISSORS + DRAW
        ),
    )

    private val counterPlays = mapOf(
        'X' to mapOf(
            'A' to SCISSORS + LOSS,
            'B' to ROCK + LOSS,
            'C' to PAPER + LOSS
        ),
        'Y' to mapOf(
            'A' to ROCK + DRAW,
            'B' to PAPER + DRAW,
            'C' to SCISSORS + DRAW
        ),
        'Z' to mapOf(
            'A' to PAPER + WIN,
            'B' to SCISSORS + WIN,
            'C' to ROCK + WIN
        ),
    )

}