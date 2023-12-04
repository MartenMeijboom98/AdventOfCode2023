package aoc.y2023.d4

import aoc.BaseSolver
import org.junit.jupiter.api.Test
import kotlin.math.min
import kotlin.math.pow

class Solver : BaseSolver(day = 4) {

    private val cards = input.map { Card.fromLine(it) }

    @Test
    fun `Solve part 1`() {
        println(cards.sumOf { it.points() })
    }

    @Test
    fun `Solve part 2`() {
        cards.forEach { it.makeCopies(cards) }
    }

}

class Card(
    val number: Int,
    val winningNumbers: List<Int>,
    val myNumbers: List<Int>,
    private val copies: MutableList<Card> = mutableListOf()
) {

    companion object {

        private val numberRegex = "[0-9]+".toRegex()

        fun fromLine(line: String): Card {
            val numbers = numberRegex.findAll(line).map { it.value.toInt() }.toList()

            return Card(
                number = numbers[0],
                winningNumbers = numbers.subList(1, 11),
                myNumbers = numbers.subList(11, numbers.size),
            )

        }
    }

    private fun copy(copyFrom: Card) =
        Card(
            number = this.number,
            winningNumbers = copyFrom.winningNumbers,
            myNumbers = copyFrom.myNumbers,
        )

    fun makeCopies(cards: List<Card>) {
        val range = min(number + points(), cards.size)
        copies.addAll((number..<range).map {
            copy(cards[it])
        })

        for (copy in copies) {
            copy.makeCopies(copies)
        }
    }

    fun points() = myNumbers.count { winningNumbers.contains(it) }.let { points ->
        if (points <= 1) points else 2.0.pow(points - 1).toInt()
    }

}