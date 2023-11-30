package aoc.y2022.d1

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(year = 2022, day = 1) {

    @Test
    fun solve() {
        solvePartOne()
        solvePartTwo()
    }

    private fun solvePartOne() {
        val topElfCapacity = getElfCapacities().maxOf { it }
        println("Most weight carries by an elf: $topElfCapacity")
    }

    private fun solvePartTwo() {
        val topThreeElvesCapacity = getElfCapacities().sortedDescending().subList(0, 3).sum()
        println("Most weight carries by top 3 elves: $topThreeElvesCapacity")
    }

    private fun getElfCapacities(): List<Int> =
        input.split { it.isBlank() }.map { it -> it.sumOf { it.toInt() } }

}