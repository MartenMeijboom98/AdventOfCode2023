package aoc.y2023.d10

import aoc.BaseSolver
import org.junit.jupiter.api.Test

private const val RED_COLOUR = "\u001b[31m"
private const val RESET_COLOUR = "\u001b[0m"

class Solver : BaseSolver(day = 10) {

    @Test
    fun `Test part 1`() {
        val testInput = listOf(
            "..F7.",
            ".FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ..."
        )

        val network = Network(testInput)

        network.findMainLoop()
        network.print()

        val maxDistance = network.getMaxDistanceFromStart()
        println(maxDistance)
        assert(maxDistance == 8)
    }

    @Test
    fun `Solve part 1`() {
        val network = Network(input)
        network.findMainLoop()
        network.print()
        println(network.getMaxDistanceFromStart())
    }

}

class Network(
    val input: List<String>
) {

    private lateinit var startingLocation: Pair<Int, Int>
    private val rows: List<List<Char>> = input.map { row ->
        row.map { it }
    }
    private val mainLoop: MutableList<LoopPart> = mutableListOf()

    init {

        loop@ for (y in rows.indices) {
            val row = rows[y]
            for (x in row.indices) {
                if (row[x] == 'S') {
                    startingLocation = Pair(x, y)
                    break@loop
                }
            }
        }

        mainLoop.add(
            LoopPart(
                location = startingLocation,
                type = 'S',
                from = Direction.NORTH,
                distance = 0
            )
        )
    }

    fun findMainLoop() {
        val north = Pair(startingLocation.first, startingLocation.second + 1)
        val south = Pair(startingLocation.first, startingLocation.second - 1)
        val west = Pair(startingLocation.first + 1, startingLocation.second)
        val east = Pair(startingLocation.first - 1, startingLocation.second)

        if (canEnter(north, Direction.NORTH)) {
            mainLoop.add(
                LoopPart(
                    location = north,
                    type = rows.getValueForLocation(north),
                    from = Direction.NORTH,
                    distance = 1
                )
            )
        } else if (canEnter(south, Direction.SOUTH)) {
            mainLoop.add(
                LoopPart(
                    location = south,
                    type = rows.getValueForLocation(south),
                    from = Direction.SOUTH,
                    distance = 1
                )
            )
        } else if (canEnter(east, Direction.EAST)) {
            mainLoop.add(
                LoopPart(
                    location = east,
                    type = rows.getValueForLocation(east),
                    from = Direction.EAST,
                    distance = 1
                )
            )
        } else if (canEnter(west, Direction.WEST)) {
            mainLoop.add(
                LoopPart(
                    location = west,
                    type = rows.getValueForLocation(west),
                    from = Direction.WEST,
                    distance = 1
                )
            )
        }

        var current = mainLoop.last()
        while (current.type != 'S') {
            current = current.findNext(rows)
            mainLoop.add(current)
        }
    }

    fun getMaxDistanceFromStart() = mainLoop[mainLoop.size / 2].distance

    private fun canEnter(nextLocation: Pair<Int, Int>, from: Direction): Boolean {
        val next = rows.getValueForLocation(nextLocation)
        return when (next) {
            '|' -> from == Direction.NORTH || from == Direction.SOUTH
            '-' -> from == Direction.EAST || from == Direction.WEST
            'L' -> from == Direction.NORTH || from == Direction.EAST
            'J' -> from == Direction.NORTH || from == Direction.WEST
            '7' -> from == Direction.SOUTH || from == Direction.WEST
            'F' -> from == Direction.SOUTH || from == Direction.EAST
            '.' -> false
            'S' -> {
                println("GOT BACK TO S")
                true
            }

            else -> throw IllegalStateException("Unknown char $nextLocation found")
        }
    }

    fun print() {
        rows.forEachIndexed { y, row ->
            println()
            row.forEachIndexed { x, column ->
                if (mainLoop.any { it.location == Pair(x, y) }) {
                    print("$RED_COLOUR$column$RESET_COLOUR")
                } else {
                    print(column)
                }
            }
        }
    }

    fun printDistances() {
        rows.forEachIndexed { y, row ->
            println()
            row.forEachIndexed { x, column ->
                val mainLoopItem = mainLoop.find { it.location == Pair(x, y) }
                if (mainLoopItem != null) {
                    print("$RED_COLOUR${mainLoopItem.distance}$RESET_COLOUR")
                } else {
                    print(column)
                }
            }
        }
    }

}

data class LoopPart(
    val location: Pair<Int, Int>,
    val type: Char,
    val from: Direction,
    val distance: Int
) {

    fun findNext(rows: List<List<Char>>): LoopPart =
        when (type) {
            '|' -> if (from == Direction.SOUTH) findNextNorth(rows) else findNextSouth(rows)
            '-' -> if (from == Direction.WEST) findNextEast(rows) else findNextWest(rows)
            'L' -> if (from == Direction.NORTH) findNextEast(rows) else findNextNorth(rows)
            'J' -> if (from == Direction.NORTH) findNextWest(rows) else findNextNorth(rows)
            '7' -> if (from == Direction.SOUTH) findNextWest(rows) else findNextSouth(rows)
            'F' -> if (from == Direction.SOUTH) findNextEast(rows) else findNextSouth(rows)
            else -> throw java.lang.IllegalStateException("Found type $type in findNext()")
        }

    private fun findNextNorth(rows: List<List<Char>>): LoopPart {
        val nextLocation = Pair(this.location.first, this.location.second - 1)
        return LoopPart(
            location = nextLocation,
            type = rows.getValueForLocation(nextLocation),
            from = Direction.SOUTH,
            distance = this.distance + 1
        )
    }


    private fun findNextSouth(rows: List<List<Char>>): LoopPart {
        val nextLocation = Pair(this.location.first, this.location.second + 1)
        return LoopPart(
            location = nextLocation,
            type = rows.getValueForLocation(nextLocation),
            from = Direction.NORTH,
            distance = this.distance + 1
        )
    }

    private fun findNextEast(rows: List<List<Char>>): LoopPart {
        val nextLocation = Pair(this.location.first + 1, this.location.second)
        return LoopPart(
            location = nextLocation,
            type = rows.getValueForLocation(nextLocation),
            from = Direction.WEST,
            distance = this.distance + 1
        )
    }

    private fun findNextWest(rows: List<List<Char>>): LoopPart {
        val nextLocation = Pair(this.location.first - 1, this.location.second)
        return LoopPart(
            location = nextLocation,
            type = rows.getValueForLocation(nextLocation),
            from = Direction.EAST,
            distance = this.distance + 1
        )
    }
}

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
}

private fun List<List<Char>>.getValueForLocation(location: Pair<Int, Int>) =
    this.getValueForLocation(location.first, location.second)

private fun List<List<Char>>.getValueForLocation(x: Int, y: Int) =
    this[y][x]