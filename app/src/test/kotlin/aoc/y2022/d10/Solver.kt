package aoc.y2022.d10

import aoc.BaseSolver
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

class Solver : BaseSolver(year = 2022, day = 10) {

    @Test
    fun `Solve part 1`() {
        val cycleValues = input.getCycleValues()

        println(
            (20..cycleValues.size).filter { it % 40 == 20 && it < 221 }.sumOf { it * cycleValues[it - 1] }
        )
    }

    @Test
    fun `Solve part 2`() {
        val cycleValues = input.getCycleValues()

        println(
            cycleValues.mapIndexed { pixel, position ->
                (position - (pixel % 40)).absoluteValue <= 1
            }.windowed(40, 40).forEach { row ->
                row.forEach { column ->
                    print(if (column) '#' else ' ')
                }
                println()
            }
        )
    }

    private fun List<String>.getCycleValues(): List<Int> =
        this.fold(mutableListOf(1)) { acc, row ->
            acc.add(0)

            if (row.contains("addx")) {
                acc.add(row.split(" ")[1].toInt())
            }

            acc
        }.runningReduce(Int::plus)

}