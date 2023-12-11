package aoc.y2023.d11

import aoc.BaseSolver
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.min

class Solver : BaseSolver(day = 11) {

    @Test
    fun `Solve part 1`() {
        val universe = ExpandedUniverse.fromLines(input, expandBy = 1)
        val result = universe.calculateDistancesBetweenGalaxies()
        println(result)
    }

    @Test
    fun `Solve part 2`() {
        val universe = ExpandedUniverse.fromLines(input, expandBy = 999999)
        val result = universe.calculateDistancesBetweenGalaxies()
        println(result)
    }

}

class ExpandedUniverse(
    private val rows: List<List<Char>>,
    private val expandBy: Int,
    private val expandedColumns: List<Int>,
    private val expandedRows: List<Int>
) {

    private val galaxies: List<Pair<Int, Int>> = rows.mapIndexed { y, row ->
        row.mapIndexed { x, item ->
            if (item == '#') Pair(x, y) else null
        }
    }.flatten().filterNotNull()

    companion object {
        fun fromLines(input: List<String>, expandBy: Int = 1): ExpandedUniverse {
            val result = input.map { row ->
                row.map { it }
            }

            return result.expand(expandBy)
        }

        private fun List<List<Char>>.expand(expandBy: Int): ExpandedUniverse {
            val expandedRows = mutableListOf<Int>()
            val expandedColumns = mutableListOf<Int>()

            this.forEachIndexed { index, row ->
                if (row.all { it == '.' }) {
                    expandedRows.add(index)
                }
            }

            this[0].forEachIndexed { index, _ ->
                val columns = this.map { it[index] }
                if (columns.all { it == '.' }) {
                    expandedColumns.add(index)
                }
            }

            return ExpandedUniverse(
                rows = this,
                expandBy = expandBy,
                expandedRows = expandedRows,
                expandedColumns = expandedColumns
            )
        }
    }

    fun calculateDistancesBetweenGalaxies(): BigInteger =
        galaxies.map { a -> galaxies.map { b -> Pair(a, b) } }.flatten().filter { it.first != it.second }
            .sumOf { calculateDistance(it.first, it.second) } / BigInteger.valueOf(2)

    private fun calculateDistance(a: Pair<Int, Int>, b: Pair<Int, Int>): BigInteger {
        val xDifference = abs(a.first - b.first)
        val yDifference = abs(a.second - b.second)

        val smallestX = min(a.first, b.first)
        val expandedColumnsCrossed = (smallestX + 1..<smallestX + xDifference).count { expandedColumns.contains(it) }

        val smallestY = min(a.second, b.second)
        val expandedRowsCrossed = (smallestY + 1..<smallestY + yDifference).count { expandedRows.contains(it) }

        return (xDifference + yDifference + (expandedRowsCrossed * expandBy) + (expandedColumnsCrossed * expandBy)).toBigInteger()
    }

}