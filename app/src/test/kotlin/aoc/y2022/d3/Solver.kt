package aoc.y2022.d3

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(year = 2022, day = 3) {

    private val codes = 65..122

    @Test
    fun `Solve part 1`() {
        println(input.sumOf { row ->
            codes.find { priority ->
                row.chunked(row.length / 2).all { it.contains(Char(priority)) }
            }!!.getPriority()
        })
    }

    @Test
    fun `Solve part 2`() {
        println(input.chunked(3).sumOf { group ->
            codes.find { code -> group.all { row -> row.contains(Char((code))) } }!!.getPriority()
        })
    }

    private fun Int.getPriority() = if (this < 91) this - 38 else this - 96

}