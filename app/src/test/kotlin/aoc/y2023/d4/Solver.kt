package aoc.y2023.d4

import aoc.BaseSolver
import org.junit.jupiter.api.Test
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.pow

class Solver : BaseSolver(day = 4) {

    private val cards = input.map { Card.fromLine(it) }

    @Test
    fun `Solve part 1`() {
        println(cards.sumOf { it.points() })
    }

    @Test
    fun `Solve part 2`() {
        val copies = ConcurrentLinkedQueue(cards)

        for (card in copies) {
            card.getCopies(cards).forEach {
                copies.add(it)
            }
        }

        println(copies.size)
    }

}

class Card(
    val number: Int,
    val winningNumbers: List<Int>,
    val myNumbers: List<Int>,
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

    fun getCopies(cards: List<Card>): List<Card> =
        (0..<winningNumbers()).map {
            cards[it + number]
        }

    fun points() = winningNumbers().let { points ->
        if (points <= 1) points else 2.0.pow(points - 1).toInt()
    }

    private fun winningNumbers() = myNumbers.count { winningNumbers.contains(it) }

}