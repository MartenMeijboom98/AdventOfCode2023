package aoc.y2023.d6

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(day = 6) {

    private val numberRegex = "[0-9]+".toRegex()

    private val times = numberRegex.findAll(input[0]).map { it.value.toLong() }.toList()
    private val distances = numberRegex.findAll(input[1]).map { it.value.toLong() }.toList()

    private val totalTime = numberRegex.findAll(input[0]).map { it.value }.joinToString("").toLong()
    private val totalDistance = numberRegex.findAll(input[1]).map { it.value }.joinToString("").toLong()

    @Test
    fun `Solve part 1`() {
        println(
            times.indices.map { index ->
                val time = times[index]
                val currentRecord = distances[index]

                (0..time).map { holdDownTime ->
                    calculateDistance(holdDownTime, time)
                }.count { it > currentRecord }
            }.reduce { acc, curr -> acc * curr }
        )
    }

    @Test
    fun `Solve part 2`() {
        var result = 0

        for(holdDownTime in (0..totalTime)) {
            if(calculateDistance(holdDownTime, totalTime) > totalDistance) {
                result++
            }
        }

        println(result)
    }

    private fun calculateDistance(holdDownTime: Long, totalRaceTime: Long) =
        holdDownTime * (totalRaceTime - holdDownTime)

}