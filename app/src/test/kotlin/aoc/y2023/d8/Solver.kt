package aoc.y2023.d8

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(day = 8) {

    private val instructions = input[0].toList()

    private val nodes = parseNodes(input)

    private fun parseNodes(lines: List<String>) = lines.drop(2).fold(mutableMapOf<String, Node>()) { acc, curr ->
        val node = Node.fromLine(curr)
        acc[node.node] = node
        acc
    }

    @Test
    fun `Solve part 1`() {
        var steps = 0L
        var node: Node = nodes["AAA"]!!

        loop@ while (true) {
            for (instruction in instructions) {
                steps++

                node = if (instruction == 'R') {
                    nodes[node.right]!!
                } else {
                    nodes[node.left]!!
                }

                if (node.node == "ZZZ") {
                    break@loop
                }
            }
        }

        println(steps)
    }

    @Test
    fun `Solve part 2`() {
        val paths = nodes.filter { it.key.endsWith('A') }.map { it.value }.toMutableList()

        val results = paths.map { path ->
            var steps = 0L
            var node = path
            loop@ while (true) {
                for (instruction in instructions) {
                    steps++

                    node = if (instruction == 'R') {
                        nodes[node.right]!!
                    } else {
                        nodes[node.left]!!
                    }

                    if (node.node.endsWith('Z')) {
                        break@loop
                    }
                }
            }
            steps
        }

        println(results.reduce { a, b -> leastCommonMultiple(a, b) })

    }

    private fun leastCommonMultiple(a: Long, b: Long): Long {
        var greatestCommonDivisor = 1
        var i = 1
        while (i <= a && i <= b) {
            if (a % i == 0L && b % i == 0L)
                greatestCommonDivisor = i
            ++i
        }

        return a * b / greatestCommonDivisor
    }

}

class Node(
    val node: String,
    val left: String,
    val right: String
) {

    companion object {

        private val itemRegex = "([A-Z]{3})".toRegex()

        fun fromLine(line: String): Node {
            val items = itemRegex.findAll(line).map { it.value }.toList()
            return Node(
                node = items[0],
                left = items[1],
                right = items[2]
            )
        }
    }

}
