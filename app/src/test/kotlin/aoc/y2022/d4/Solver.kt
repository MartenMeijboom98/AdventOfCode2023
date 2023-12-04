package aoc.y2022.d4

import aoc.BaseSolver
import org.junit.jupiter.api.Test

class Solver : BaseSolver(year = 2022, day = 4) {

    @Test
    fun `Solve part 1`() {
        println(input.count { row ->
            val sections = row.split(",")
            sections[0].fullyContains(sections[1]) || sections[1].fullyContains(sections[0])
        })
    }

    @Test
    fun `Solve part 2`() {
        println(input.count { row ->
            val sections = row.split(",")
            sections[0].contains(sections[1]) || sections[1].contains(sections[0])
        })
    }

    private fun String.fullyContains(another: String): Boolean =
        this.split("-")
            .let { myself ->
                another.split("-")
                    .let { other ->
                        myself[0].toInt() <= other[0].toInt() && myself[1].toInt() >= other[1].toInt()
                    }
            }

    private fun String.contains(another: String): Boolean =
        this.split("-")
            .let { myself ->
                another.split("-")
                    .let { other ->
                        myself[0].toInt() <= other[1].toInt() && myself[1].toInt() >= other[0].toInt()
                    }
            }

}