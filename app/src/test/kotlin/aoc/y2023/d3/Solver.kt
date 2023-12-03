package aoc.y2023.d3

import aoc.BaseSolver
import org.junit.jupiter.api.Test
import kotlin.math.absoluteValue

class Solver : BaseSolver(day = 3) {

    private val numberRegex = "[0-9]+".toRegex()

    private val schematic = input
        .toMutableList()
        .foldIndexed(arrayOfNulls<Array<Char?>>(input.size)) { y, rows, row ->
            rows[y] = row.foldIndexed(arrayOfNulls(row.length)) { x, columns, column ->
                columns[x] = column
                columns
            }
            rows
        }

    @Test
    fun `Solve part 1`() {
        println(input.mapIndexed { y, row ->
            numberRegex.findAll(row).map { matchResult ->
                if (matchResult.range.any { hasAdjacentSymbol(it, y) }) {
                    matchResult.value.toInt()
                } else {
                    0
                }
            }
        }.sumOf { it.sum() })
    }

    @Test
    fun `Solve part 2`() {
        println(
            input.mapIndexed { y, row ->
                row.mapIndexed { x, column ->
                    if (column == '*') {
                        val adjacentNumbers = getAdjacentNumbers(x, y)
                        if (adjacentNumbers.size == 2) {
                            adjacentNumbers[0] * adjacentNumbers[1]
                        } else {
                            0
                        }
                    } else {
                        0
                    }
                }
            }.flatten().sum()
        )
    }

    private fun hasAdjacentSymbol(x: Int, y: Int): Boolean =
        (x.minus(1)..x.plus(1)).any { xToCheck ->
            (y.minus(1)..y.plus(1)).any { yToCheck ->
                try {
                    !schematic[yToCheck]!![xToCheck]!!.isDigit() && schematic[yToCheck]!![xToCheck]!! != '.'
                } catch (e: ArrayIndexOutOfBoundsException) {
                    false
                }
            }
        }

    private fun getAdjacentNumbers(x: Int, y: Int): List<Int> =
        (y.minus(1)..y.plus(1)).map { yToCheck ->
            numberRegex.findAll(schematic[yToCheck]!!.joinToString("")).map { match ->
                if (match.range.any { (it - x).absoluteValue <= 1 }) {
                    match.value.toInt()
                } else {
                    null
                }
            }.filterNotNull().toList()
        }.flatten()

    init {
        schematic.forEach { row ->
            row!!.forEach { col ->
                print(col)
            }
            println()
        }
    }

}