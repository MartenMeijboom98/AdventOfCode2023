package aoc.y2022.d11

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(year = 2022, day = 11) {

    private val monkeys: List<Monkey> = input.chunked(7).map { Monkey.fromRows(it) }

    @Test
    fun `Solve part 1`() {
        repeat(20) {
            monkeys.forEach { monkey -> monkey.inspectItems(monkeys) { it / 3 } }
        }

        println(monkeys.business())
    }

    @Test
    fun `Solve part 2`() {
        val testProduct: Long = monkeys.map { it.test }.reduce(Long::times)

        repeat(10000) {
            monkeys.forEach { monkey -> monkey.inspectItems(monkeys) { it % testProduct } }
        }

        println(monkeys.business())
    }

}

private fun List<Monkey>.business(): Long =
    this.sortedByDescending { it.interactions }.let { it[0].interactions * it[1].interactions }

private class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: Long,
    val trueMonkey: Int,
    val falseMonkey: Int
) {

    var interactions: Long = 0

    fun inspectItems(monkeys: List<Monkey>, changeToWorryLevel: (Long) -> Long) {
        items.forEach { item ->
            val worry = changeToWorryLevel(operation(item))
            val target = if (worry % test == 0L) trueMonkey else falseMonkey
            monkeys[target].items.add(worry)
        }
        interactions += items.size
        items.clear()
    }

    companion object {
        fun fromRows(input: List<String>): Monkey {
            val items = input[1].substringAfter(": ").split(", ").map { it.toLong() }.toMutableList()
            val operationValue = input[2].substringAfterLast(" ")
            val operation: (Long) -> Long = when {
                operationValue == "old" -> ({ it * it })
                '*' in input[2] -> ({ it * operationValue.toLong() })
                else -> ({ it + operationValue.toLong() })
            }
            val test = input[3].substringAfterLast(" ").toLong()
            val trueMonkey = input[4].substringAfterLast(" ").toInt()
            val falseMonkey = input[5].substringAfterLast(" ").toInt()
            return Monkey(
                items,
                operation,
                test,
                trueMonkey,
                falseMonkey
            )
        }
    }
}