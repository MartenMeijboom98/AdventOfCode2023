package aoc.y2023.d7.p1

import aoc.BaseSolver
import org.junit.jupiter.api.Test

val cardValues = mapOf(
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9,
    'T' to 10,
    'J' to 11,
    'Q' to 12,
    'K' to 13,
    'A' to 14
)

class Part1Solver : BaseSolver(day = 7) {

    private val hands = input.map { Hand.fromLine(it) }

    @Test
    fun `Solve part 1`() {
        println(
            hands.sortedWith(HandComparator()).mapIndexed { index, hand ->
                (index + 1) * hand.bid
            }.sum()
        )
    }

}

data class Hand(
    val cards: List<Char>,
    val bid: Int,
) {

    private val type = HandType.forHand(this)

    companion object {
        fun fromLine(line: String): Hand {
            val split = line.split(" ")
            return Hand(
                cards = split[0].toList(),
                bid = split[1].toInt()
            )
        }
    }

    fun compareTo(other: Hand): Int {
        var result = this.type.value.compareTo(other.type.value)
        if (result != 0) {
            return result
        }

        loop@ for (i in cards.indices) {
            result = cardValues[this.cards[i]]!!.compareTo(cardValues[other.cards[i]]!!)
            if (result != 0) {
                break@loop
            }
        }

        return result
    }

}

class HandComparator : Comparator<Hand> {

    override fun compare(o1: Hand, o2: Hand): Int =
        o1.compareTo(o2)

}

sealed class HandType(val value: Int) {

    companion object {

        private val types = listOf(
            FiveOfAKind(),
            FourOfAKind(),
            FullHouse(),
            ThreeOfAKind(),
            TwoPair(),
            OnePair(),
            HighCard()
        )

        fun forHand(hand: Hand) = types.first { it.isOfType(hand) }
    }

    fun cardCounts(hand: Hand): Map<Char, Int> =
        hand.cards.fold(cardValues.keys.fold(mutableMapOf()) { acc, curr ->
            acc[curr] = 0
            acc
        }) { acc, curr ->
            acc[curr] = acc[curr]!! + 1
            acc
        }

    abstract fun isOfType(hand: Hand): Boolean
}

class FiveOfAKind : HandType(value = 7) {

    override fun isOfType(hand: Hand): Boolean =
        cardCounts(hand).any { it.value == 5 }

}

class FourOfAKind : HandType(value = 6) {

    override fun isOfType(hand: Hand): Boolean =
        cardCounts(hand).any { it.value == 4 }

}

class FullHouse : HandType(value = 5) {

    override fun isOfType(hand: Hand): Boolean =
        cardCounts(hand).let { cardCount -> cardCount.any { it.value == 3 } && cardCount.any { it.value == 2 } }

}

class ThreeOfAKind : HandType(value = 4) {
    override fun isOfType(hand: Hand): Boolean =
        cardCounts(hand).any { it.value == 3 }

}

class TwoPair : HandType(value = 3) {

    override fun isOfType(hand: Hand): Boolean =
        cardCounts(hand).filter { it.value == 2 }.size == 2

}

class OnePair : HandType(value = 2) {

    override fun isOfType(hand: Hand): Boolean =
        cardCounts(hand).any { it.value == 2 }

}

class HighCard : HandType(value = 1) {
    override fun isOfType(hand: Hand): Boolean =
        hand.cards.distinct().size == hand.cards.size

}