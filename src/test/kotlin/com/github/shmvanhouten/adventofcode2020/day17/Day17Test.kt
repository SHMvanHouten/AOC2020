package com.github.shmvanhouten.adventofcode2020.day17

import com.github.shmvanhouten.adventofcode2020.util.Coord
import com.github.shmvanhouten.adventofcode2020.util.Coord3d
import com.github.shmvanhouten.adventofcode2020.util.Coord4d
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day17Test {

//    @Test
////    internal fun `parse parses`() {
////        val input = """
////                |.#.
////                |..#
////                |###
////            """.trimMargin()
////        assertThat(
////            parse3d(input), equalTo(
////                mapOf(
////                    Coord3d(0, 0, 0) to Cube(Coord3d(0, 0, 0), false),
////                    Coord3d(1, 0, 0) to Cube(Coord3d(1, 0, 0), true),
////                    Coord3d(2, 0, 0) to Cube(Coord3d(2, 0, 0), false),
////                    Coord3d(0, 1, 0) to Cube(Coord3d(0, 1, 0), false),
////                    Coord3d(1, 1, 0) to Cube(Coord3d(1, 1, 0), false),
////                    Coord3d(2, 1, 0) to Cube(Coord3d(2, 1, 0), true),
////                    Coord3d(0, 2, 0) to Cube(Coord3d(0, 2, 0), true),
////                    Coord3d(1, 2, 0) to Cube(Coord3d(1, 2, 0), true),
////                    Coord3d(2, 2, 0) to Cube(Coord3d(2, 2, 0), true)
////                )
////            )
////        )
//    }


    @Nested
    inner class Part1 {

        @Test
        internal fun `a cube that is inactive with 3 active neighbours becomes active`() {
            val input = """
                |.#.
                |..#
                |.#.
            """.trimMargin()
            val pocketDimension = PocketDimension(parse3d(input))
            assertThat(
                pocketDimension.cubes.values,
                hasElement((Cube(Coord3d(1, 1, 0), false)))
            )

            assertThat(
                pocketDimension.cycle().cubes.values,
                hasElement((Cube(Coord3d(1, 1, 0), true)))
            )
        }

        @Test
        internal fun `example input`() {
            val input = """
                |.#.
                |..#
                |###
            """.trimMargin()
            val pocketDimension = PocketDimension(parse3d(input))
            assertThat(pocketDimension.activeCubes().size, equalTo(5))

            val after1Cycle = pocketDimension.cycle()
            assertThat(after1Cycle.activeCubes().size, equalTo(11))

            val after2Cycles = after1Cycle.cycle()
            assertThat(after2Cycles.activeCubes().size, equalTo(21))

            val after3Cycles = after2Cycles.cycle()
            assertThat(after3Cycles.activeCubes().size, equalTo(38))

            val after6Cycles = pocketDimension.cycle(6)
            assertThat(after6Cycles.activeCubes().size, equalTo(112))
        }

        @Test
        internal fun `part 1`() {
            val input = """
                |##....#.
                |#.#..#..
                |...#....
                |...#.#..
                |###....#
                |#.#....#
                |.#....##
                |.#.###.#
            """.trimMargin()

            val pocketDimension = PocketDimension(parse3d(input))
            assertThat(pocketDimension.cycle(6).activeCubes().size, equalTo(263))
        }
    }

    @Nested
    inner class Part2 {

        @Test
        internal fun `example input`() {
            val input = """
                |.#.
                |..#
                |###
            """.trimMargin()
            val pocketDimension = PocketDimension(parse4d(input))
            assertThat(pocketDimension.activeCubes().size, equalTo(5))

            assertThat(pocketDimension.cycle().activeCubes().size, equalTo(29))
            assertThat(pocketDimension.cycle(6).activeCubes().size, equalTo(848))

        }

        @Test
        internal fun `part 2`() {
            val input = """
                |##....#.
                |#.#..#..
                |...#....
                |...#.#..
                |###....#
                |#.#....#
                |.#....##
                |.#.###.#
            """.trimMargin()

            val pocketDimension = PocketDimension(parse4d(input))
            assertThat(pocketDimension.cycle(6).activeCubes().size, equalTo(1680))
        }
    }

    private fun parse3d(input: String): Map<Coord, Cube> {
        return input.lines()
            .mapIndexed { y, line -> parseLine(y, line) }
            .flatten()
            .map { it.location to it }
            .toMap()
    }

    private fun parse4d(input: String): Map<Coord, Cube> {
        return input.lines()
            .mapIndexed { y, line -> parse4dLine(y, line) }
            .flatten()
            .map { it.location to it }
            .toMap()
    }

    private fun parse4dLine(y: Int, line: String): List<Cube> {
        return line.mapIndexed { x, c ->
            to4dCube(c, x, y)
        }
    }

    private fun to4dCube(c: Char, x: Int, y: Int): Cube {
        return Cube(Coord4d(x, y, 0, 0), c == '#')
    }

    private fun parseLine(y: Int, line: String): List<Cube> {
        return line.mapIndexed { x, c ->
            to3dCube(c, x, y)
        }
    }

    private fun to3dCube(c: Char, x: Int, y: Int): Cube {
        return Cube(Coord3d(x, y, 0), c == '#')
    }

}
