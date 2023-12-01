package aoc.y2023.d1

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(day = 1) {

    @Test
    fun `Solve day 1`() {
        solvePartOne()
        solvePartTwo()
    }

    private fun solvePartOne() {
        val result = input.sumOf { "${it.findFirstDigit()}${it.findLastDigit()}".toInt() }
        println("Sum of calibration values is $result")
    }

    private fun solvePartTwo() {
        val result = input.sumOf { "${it.replaceFirstWrittenDigit().findFirstDigit()}${it.replaceLastWrittenDigit().findLastDigit()}".toInt() }
        println("Sum of calibration values, including written digits, is $result")
    }

    private fun String.findFirstDigit() = this.find { it.isDigit() }
    private fun String.findLastDigit() = this.findLast { it.isDigit() }

    private val writtenDigits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    private fun String.replaceFirstWrittenDigit() = writtenDigits.entries.minBy { writtenDigit ->
        this.indexOf(writtenDigit.key).let { if (it < 0) Int.MAX_VALUE else it }
    }.let { this.replace(it.key, it.value.toString()) }

    private fun String.replaceLastWrittenDigit() = writtenDigits.entries.maxBy { writtenDigit ->
        this.lastIndexOf(writtenDigit.key)
    }.let { this.replace(it.key, it.value.toString()) }

}