package aoc.y2023.d5

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(day = 5) {

    private val numberRegex = "[0-9]+".toRegex()

    private val splitInput = input.split { it.isBlank() }

    private val seedRanges = numberRegex.findAll(splitInput[0][0]).map { it.value.toLong() }.chunked(2).map {
        val r = it.toList()
        (r[0]..<(r[0] + r[1]))
    }.toList()

    private val mappings = splitInput.drop(1).map { block ->
        block.drop(1).map { row ->
            val numbers = numberRegex.findAll(row).map { it.value.toLong() }.toList()
            Mapping(
                sourceStart = numbers[1],
                destinationStart = numbers[0],
                range = numbers[2]
            )
        }
    }

    @Test
    fun `Solve part 2`() {
        var location = 0L
        var running = true
        while (running) {
            location++

            val seed = findSeed(location)

            if (seedRanges.any { it.contains(seed) }) {
                running = false
            }
        }

        println(location)
    }

    private fun findSeed(location: Long) =
        mappings.reversed().fold(mutableListOf(location)) { acc, mapping ->
            acc[0] = mapping.getSource(acc[0])
            acc
        }[0]

    private fun List<Mapping>.getSource(destination: Long): Long {
        this.forEach { mapping ->
            val result = mapping.getSourceFor(destination)
            if (result != destination) {
                return result
            }
        }

        return destination
    }

}

data class Mapping(
    val sourceStart: Long,
    val destinationStart: Long,
    val range: Long
) {

    private val destinationEnd = destinationStart + range - 1

    override fun toString(): String =
        "From $destinationStart - $destinationEnd to $sourceStart - ${sourceStart + range - 1}"

    fun getSourceFor(destination: Long): Long {
        if (destination in destinationStart..destinationEnd) {
            return sourceStart + (destination - destinationStart)
        }

        return destination
    }

}