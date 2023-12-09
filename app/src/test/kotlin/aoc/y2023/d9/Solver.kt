package aoc.y2023.d9

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(day = 9) {

    @Test
    fun `Test both parts`() {
        val testInput = listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45"
        )

        val testHistory = testInput.map { ValueHistory.fromInput(it) }

        val p1result = testHistory.sumOf { it.findNextValue() }
        println(p1result)
        assert(p1result == 114)

        val p2result = testHistory.sumOf { it.findPreviousValue() }
        println(p2result)
        assert(p2result == 2)
    }

    @Test
    fun `Solve part 1`() {
        val history = input.map { ValueHistory.fromInput(it) }
        println(history.sumOf { it.findNextValue() })
    }

    @Test
    fun `Solve part 2`() {
        val history = input.map { ValueHistory.fromInput(it) }
        println(history.sumOf { it.findPreviousValue() })
    }

}

class ValueHistory(
    private val history: MutableList<Int>
) {

    private val differences = mutableListOf<MutableList<Int>>()

    init {
        var current = history
        while (current.first() != 0 || current.last() != 0) {
            current = calculateDifferences(current)
            differences.add(current)
        }
    }

    companion object {

        fun fromInput(input: String) =
            ValueHistory(input.split(" ").map { it.toInt() }.toMutableList())

    }

    fun findNextValue(): Int {
        differences.reversed().drop(1).forEachIndexed { index, difference ->
            val prev = differences.reversed()[index]
            difference.add(prev.last() + difference.last())
        }

        val result = differences.first().last() + history.last()
        history.add(result)
        return result
    }

    fun findPreviousValue(): Int {
        differences.reversed().drop(1).forEachIndexed { index, difference ->
            val prev = differences.reversed()[index]
            difference.add(0, difference.first() - prev.first())
        }

        val result = history.first() - differences.first().first()
        history.add(0, result)
        return result
    }

    private fun calculateDifferences(line: List<Int>): MutableList<Int> {
        val result = mutableListOf<Int>()
        for (i in (1..<line.size)) {
            result.add(line[i] - line[i - 1])
        }
        return result
    }

}