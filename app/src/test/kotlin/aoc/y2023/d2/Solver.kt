package aoc.y2023.d2

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(day = 2) {

    private val numberRegex = "[0-9]+".toRegex()

    private val redRegex = "[0-9]+ (red)".toRegex()
    private val blueRegex = "[0-9]+ (blue)".toRegex()
    private val greenRegex = "[0-9]+ (green)".toRegex()

    @Test
    fun `Solve part 1`() {
        println(input.sumOf { row ->
            row.split(";").all { hand ->
                (redRegex.find(hand)?.value?.let { numberRegex.find(it)!!.value.toInt() } ?: 0) <= 12 &&
                (greenRegex.find(hand)?.value?.let { numberRegex.find(it)!!.value.toInt() } ?: 0) <= 13 &&
                (blueRegex.find(hand)?.value?.let { numberRegex.find(it)!!.value.toInt() } ?: 0) <= 14
            }.let { if (it) numberRegex.find(row)!!.value.toInt() else 0 }
        })
    }

    @Test
    fun `Solve part 2`() {
        println(input.sumOf { row ->
            redRegex.findAll(row).maxOf { numberRegex.find(it.value)!!.value.toInt() } *
            greenRegex.findAll(row).maxOf { numberRegex.find(it.value)!!.value.toInt() } *
            blueRegex.findAll(row).maxOf { numberRegex.find(it.value)!!.value.toInt() }
        })
    }

}