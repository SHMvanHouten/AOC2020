package com.github.shmvanhouten.adventofcode2020.day03

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day03Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `find the amount of trees on the way where none are there`() {
            val input = """
                |...
                |...
                |...
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input)), equalTo(0))
        }

        @Test
        internal fun `find the amount of trees on the way where one is in the way`() {
            val input = """
                |....
                |...#
                |....
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input)), equalTo(1))
        }

        @Test
        internal fun `trees that are not in the way do not count`() {
            val input = """
                |....
                |#...
                |....
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input)), equalTo(0))
        }

        @Test
        internal fun `the second step has a tree`() {
            val input = """
                |........
                |........
                |......#.
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input)), equalTo(1))
        }

        @Test
        internal fun `we support the pattern repeating on the horizontal plane`() {
            val input = """
                |....
                |#...
                |..#.
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input)), equalTo(1))
        }

        @Test
        internal fun `third step`() {
            val input = """
                |...
                |...
                |#..
                |#..
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input)), equalTo(2))
        }

        @Test
        internal fun `third step, width 4`() {
            val input = """
                |....
                |...#
                |..#.
                |.#..
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input)), equalTo(3))
        }

        @Test
        internal fun `part 1`() {
            assertThat(countTreesInPath(toSlope(day3Input)), equalTo(164))
        }
    }

    @Nested
    inner class Part2 {
        @Test
        internal fun `it works for different step size on the x plane`() {
            val input = """
                |....
                |#...
                |....
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input), 4, 1), equalTo(1))
        }

        @Test
        internal fun `it works for different step size on the y plane`() {
            val input = """
                |....
                |....
                |.#..
            """.trimMargin()
            assertThat(countTreesInPath(toSlope(input), 1, 2), equalTo(1))
        }

        @Test
        internal fun `test inputs`() {
            assertThat(countTreesInPath(toSlope(testInput), 1, 1), equalTo(2))
            assertThat(countTreesInPath(toSlope(testInput), 3, 1), equalTo(7))
            assertThat(countTreesInPath(toSlope(testInput), 5, 1), equalTo(3))
            assertThat(countTreesInPath(toSlope(testInput), 7, 1), equalTo(4))
            assertThat(countTreesInPath(toSlope(testInput), 1, 2), equalTo(2))
        }

        @Test
        internal fun `test inputs multiplied`() {
            val r1d1 = countTreesInPath(toSlope(testInput), 1, 1)
            val r3d1 = countTreesInPath(toSlope(testInput), 3, 1)
            val r5d1 = countTreesInPath(toSlope(testInput), 5, 1)
            val r7d1 = countTreesInPath(toSlope(testInput), 7, 1)
            val r1d2 = countTreesInPath(toSlope(testInput), 1, 2)

            assertThat(r1d1 * r3d1 * r5d1 * r7d1 * r1d2, equalTo(336))
        }

        @Test
        internal fun `part 2`() {
            val r1d1 = countTreesInPath(toSlope(day3Input), 1, 1)
            val r3d1 = countTreesInPath(toSlope(day3Input), 3, 1)
            val r5d1 = countTreesInPath(toSlope(day3Input), 5, 1)
            val r7d1 = countTreesInPath(toSlope(day3Input), 7, 1)
            val r1d2 = countTreesInPath(toSlope(day3Input), 1, 2)

            assertThat(r1d1 * r3d1 * r5d1 * r7d1 * r1d2, greaterThan(712691360))
            // 712691360 too low
        }
    }

    val testInput = """
        |..##.......
        |#...#...#..
        |.#....#..#.
        |..#.#...#.#
        |.#...##..#.
        |..#.##.....
        |.#.#.#....#
        |.#........#
        |#.##...#...
        |#...##....#
        |.#..#...#.#
    """.trimMargin()

    val day3Input = """
        |....##..#........##...#.#..#.##
        |.#.#..#....##....#...#..##.....
        |##.#..##..#...#..........##.#..
        |.#.##.####..#......###.........
        |#.#.#...........#.....#...#....
        |#.......#....#.#.##..###..##..#
        |.#...#...##....#.........#.....
        |..........##.#.#.....#....#.#..
        |.......##..##...#.#.#...#......
        |.#.#.#...#...##...#.##.##..#...
        |........##.#.#.###.........##..
        |#.#..#.#.#.....#...#...#......#
        |.#.#.#...##......#...#.........
        |.#..##.##.#...#...##....#.#....
        |.##...#..#..#......##.###....##
        |.....#...#.###.....#.#.........
        |#.##..#....#.#.#.#.............
        |........#...#......#...#..#....
        |##..##...##.##...#...#.###...##
        |#.#....##.#...###......#..#.#..
        |..#.....#.##......#..........#.
        |#.......#..#......#.....#....#.
        |.....###...........#....#.##...
        |#.#........##.......#.#...#.##.
        |.#.#.#........#........#.#.....
        |#..#..##.....#.###..#.#.#.##..#
        |..#.#...#..##.#.#.#.......###..
        |........#........#..#..#...#...
        |##............#...#..##.##...#.
        |#....#.#.....##...#............
        |............#...#..#.#.#....#..
        |#.#.#...##.##.#....#....#......
        |................###.....#.....#
        |##.#####.#..#...###..#...###...
        |...#.....#...#.#....#...#..#...
        |.......#....##.##.#.##.........
        |..#..#..##.....#...#.#.....#...
        |...#...#.#.##.#..###.......#...
        |...#...........#.#####..##..#..
        |#.#...#........####..#......#.#
        |#..#.##...........#.#......#.##
        |#.#..#....##..#..##.#..........
        |.....#..#.....#........##..#...
        |...###.......#.##.......#......
        |...##..#..#...#....#.###...#...
        |....####....#........#.##.#.#.#
        |#....#.....#.###.##...##..##.##
        |.##.#...#.##.#......#..##.#....
        |...#.............#.............
        |..##..##.#.....#........##....#
        |#.....#....#.......####...#..#.
        |..#...#..#...#...##.#....##....
        |.#...##....#....#..#....#......
        |##..#.#...##......#..#.......##
        |..#...#.##..#.....#.#...#..#.#.
        |#..##....#..........#..........
        |.#........#..#......#......#.#.
        |...##.#.........#.#....#.#...#.
        |#.....#.#..#...#...#..#...#...#
        |#.........#.#.........##.......
        |.#.......#......#.........###..
        |.#..#..##...........#.....#..#.
        |.#....................#.....#..
        |.##.....#....#....#.###.....#..
        |...##.#.....#.#....#.........#.
        |.........##.....#.....#.##..#..
        |......#......#.#..#..###...#..#
        |..##...#.#..#...#.#....#.......
        |..#..##.###.#..#..#..#......#..
        |.....##...##.........#...##...#
        |###.#..##....##...##.#..###....
        |#...#.#..##......##...#.#..#...
        |..........#....###....#........
        |#.#.#.#.#.....#..##.##.....#...
        |.##.....#...#.....#......#.....
        |.#..........#.#.............#..
        |.#..##..#.#..##...#....#.##...#
        |..#.#..........#...#..........#
        |.#.......#.......#...#..#.....#
        |##.#...##...#......#.#..#......
        |#####..#....#..#...#...#.#.....
        |....#.......#.#..#.............
        |#..#..#.#.####...#....#....##..
        |#..#.##.#......#...#......#....
        |#...##.##...#....#..........##.
        |..#..#.......##.#....#...#.#...
        |.....#.##..............##.....#
        |..##.##...##.....#.........#.#.
        |.#....##...##...##..#....##..#.
        |.#...#....#..#......#.#........
        |#....#.#.#..............#....##
        |..##..#..#....#####.#....#.#.##
        |#....#...#.##..#.##.........###
        |#..#..#....#...............#.#.
        |#....##...##........##.##.#.##.
        |......#......##....##.....#.###
        |#...##..#..#.....#.#........##.
        |..#.#.##...#...#....#..###...#.
        |#..##..#.###..##.#.#....#......
        |..###..#.##........###.....#...
        |#.............#.............#..
        |.#.##....#..##.#...#....#.#####
        |###.....###.#......##..#..##...
        |.#.#......##.#....#....#.#..#..
        |###..#..#....#......###.##.....
        |......#.......#......#..##..##.
        |..#..#...#..#....#.##....#.#..#
        |.......##..#........#.#.##.....
        |.#...#..#........#..#.#..#..#.#
        |.#..#.##.......#......#...#.#..
        |.##..##......##.#...#......####
        |.....#....#......#.....#......#
        |..........#.#.#...##.#..#.#....
        |...#.......##......#..#.#.##...
        |.###..#.#.#....................
        |##...#...#.##............#.....
        |....#....#.##...#..#.#...##....
        |..#.#....#.###...#...#.#.####.#
        |..#..#.#...#.#......##.........
        |#..#..####.##.#.#..###....#...#
        |....#..........#.##.#..#.#.#.#.
        |#.#.##.........#.....##...#..##
        |#......#...#.##.#......#..#.#..
        |#...#........#..#..#...##...#..
        |.....#.####..##..#.#.##..#...#.
        |#..#........#........#...#....#
        |...........#..#.....#.#.#.#....
        |....#......#....#...#....##....
        |.#.#..#...#.#....#..#.#....##.#
        |....#...#...#.##..#...#..##...#
        |#######...............##.....##
        |.#.#..............#....#..#.###
        |#......#.#......###....###.....
        |##..#...#.##..##..##.#....#....
        |#....##..#..#...#.#.#...#......
        |..........#..#.##..##.##.#..##.
        |....#.#.#.....##........#..#...
        |..###...#.....##.##.....##..##.
        |....#.#..#.#.......#.......#...
        |..##.#..#.....##...###...#...#.
        |..#.........#...##...#...#..#..
        |..#..#..#..#..##.#...##..#.#...
        |...##..#..##..#..####...#.....#
        |............#............###...
        |.#.#.###.#.....#.#.#..#.###..#.
        |...#.........#.....####........
        |....##.#..##.#.............#...
        |....#.##.....#..#.....#......##
        |..........#.............#...##.
        |#..#.....#.......##..###.......
        |..##.#...........#.......#..#..
        |...#...#.#.##.###....#.#..#....
        |...#..........##..#..#..#...###
        |.........#.....#..##.....#..#..
        |#........#...#...#..#.#....##..
        |.#.#.....####..#.##.#..........
        |###.......##..#.##...#.....#...
        |..###.##.#..#..#..#.....##...#.
        |.........#.....##.#..#..##.....
        |#..#..##...###..............#..
        |#....#.#....#..#.....#..####...
        |####..#.....##...#..#.#.#.#...#
        |...#....#.....#.##.#.#.#....##.
        |..........#...#.....###....#.##
        |#....#.#.#....#..#..#.....#....
        |.....#.#...#......#....#......#
        |.####....##...#...#......##..#.
        |.#...#..#....#..#..............
        |##.#...##...#.##..#......#.....
        |..####.##..#....#.#......#.#.##
        |........#.....##...#.#..##....#
        |....#.#.#.#.###...#.#...##...##
        |#.....#...####.#....#.#........
        |..#.....#...##.........###.....
        |....#....#....#..#......#####.#
        |###.....#..#.#.#......#.##.#...
        |....#.##......#..#.#...........
        |.#....#....#.#..#.......#...##.
        |...................#.#.#..#....
        |##...#.....#........#....#...#.
        |........##......#...##.#..#.#.#
        |#.#..###...#....#.#...#.......#
        |#..........##......#..#..#.....
        |.............#...##.#...#......
        |#..#....##..#.........#..#.###.
        |.....#..........#....##.#...##.
        |###....................#.#.##..
        |#........##...##......#....###.
        |#....#.............#....#...#..
        |##.......##.#.......#....#..#..
        |##...#............#..#.#....##.
        |..#.#..#...#####..........#....
        |..#.........##.....#.#...####..
        |....#..............#...........
        |..#...#.#.#..#.......##.#.#.#..
        |....#.##.....##..#.....#..####.
        |#...#...#...#.......#.........#
        |......#..#.####...###.#.#.....#
        |.......#..#..#.....#.#..##.#..#
        |.#......##..#............#.....
        |.#........#.#....#....#........
        |.....#.#..#.##.#..##....#..#...
        |#.#...........#....##.....#....
        |..#..#..##.###..##..#..###.#.##
        |##.#..#...##.#.........#...#.#.
        |......#..#..##...#....#...#.##.
        |#.##.......................#...
        |.......#..#..#.##..##......#...
        |..#.#...#.....#..###....#...#..
        |##..#.....#..#..#.##.....#...##
        |#...##...###...#.#..###....#...
        |...#.#.#..####.....#...##....#.
        |.#.#..##.....#..#.....##..##..#
        |#...#..........#.##.#.#........
        |..##....#.##....#..##......#...
        |....#..........###.....####..##
        |...........###....##.#.#.#.#...
        |...#......................####.
        |#.#.#...#.#.#.#.#......#.....##
        |..###...#.####...#..##..#....#.
        |....#....#.......#...#.........
        |.#.###.............##..#...#...
        |....#.#....##...#.....#.##.....
        |#.......##.....#.#.....#....##.
        |....##.....###..#.#..#....#...#
        |......#..##...#......#.....#.##
        |.#.....#.##.###....#.....#..###
        |...#..#.###.#....#..#..#...##.#
        |...##..#...#..#.#.#..#...#.....
        |##.####...##..#.#.#....#.......
        |..##..#.#.......##.#......##.#.
        |....##....#....#..#....#..##.#.
        |..##..........##....#...#.#..#.
        |#.#...#.#.###.#.#..##.#...#....
        |.....#..#.............#...#...#
        |....#.#..#...##...#....#.##....
        |#..#...#.###.....#...#.....#.#.
        |#####....#....#....#.......#.##
        |#...##....##.#.#...#.....##.#..
        |#.......#...#..#..#...#....#...
        |....#......#.#..........#....##
        |#.###...#.#..##..#.##........#.
        |#..#.....##.......#..#..#.#....
        |...#...#.##...#....#.#.#.#...#.
        |...##..#.#....#......###......#
        |#.#....#...#..#..#....#........
        |..#..#.#...##..........#.###...
        |#..........#...#..#....#....###
        |..#..#.#....#..............#...
        |...#........#...#.#....###.#..#
        |....#.#.#................#..#.#
        |..#........##.#....#.#..#......
        |...##..#..#.......#..#......#.#
        |..#..#....#.........#....#.##..
        |#.....#....###.#..#..#...#...#.
        |..#..##.###.#..##....#.###.....
        |...#...####..#........###.#....
        |.........#.#...#..#..#.#.......
        |.##.........##.#..............#
        |..#.#.#.....###........#.#.#..#
        |....##..#....#....#.#..#.......
        |#.#.....#...#........##........
        |.##.#.#..#..#.#.#.........#....
        |#.....#..#.##...#...#..........
        |##..#....#....##.#..#.........#
        |................#.##.#......#.#
        |..#..#.#........##...###..#...#
        |##........#.......#...##.##..#.
        |##....#.....#..##....#.......#.
        |#.#....#.#........#..#.........
        |......##......#...#.....#.##...
        |###....#..........##.#.#......#
        |......#...###.........###..#...
        |.####....#...##..#.#.....#...#.
        |.##...#...###....#...#.#..###..
        |#..#......##...#.###..###...#..
        |#....#.#.#..#....##...#.##..#..
        |..#.....#...#..........#.##.###
        |#.....#....###.......##..##.#..
        |#..##...#..#....#.###......#...
        |#..#........##..#.....#.#.#....
        |#.##.#.#..#....#.#.............
        |.#...............#....##.......
        |.#.##......##........#...#..#.#
        |.#...#....#....#...#..#...##...
        |.....#..###...##........#.#....
        |...#.......#....##..#..#....#..
        |...###....#........#..#.###.#..
        |......##..##..............###.#
        |.......#.####..##....#.#....#..
        |#...#......#...#..#.....##....#
        |.#..#..###....#..##.##.#.......
        |#......##......#..##....#..##..
        |.....#..#.#......##.##..##.....
        |...#..#.......#......#.........
        |....#..####......#..#....#...#.
        |..#.#..#...#....#....#.......#.
        |####..#........#.###...##.#.#.#
        |.......##........#.#.#...##....
        |...#.........#..#.#..##....#...
        |.....#..#...#.#....#...#.#.##.#
        |#..##.....#.....##.......#...#.
        |.......##.#.#.....#....#......#
        |...#...#.##...#......#....#....
        |..#..#.#...#..#.....#...###.#..
        |.........#...#..#.......##.....
        |..##...................#..#.###
        |.##.##..#.#...#.#....#.....##..
        |#.#...##...#...#...##..#......#
        |....#..#...#.....##.#.....#..##
        |##.#..........###..#...#..#....
        |...##....#.##....#......#......
        |.....#.........#....#.#.......#
        |.......#............#.#.....#..
        |..#..#...#..#####..#....##.....
        |...##......##...#.#........##..
        |.....#..###...##.#.#.##.#...#..
        |..#.#.#..##..#.##...##.#.#.....
        |......##...#..##......#.#......
        |......................#........
        |#...#..#....#..#.#.##.#.....#.#
        |.#......#.#....#.#.#..#....#...
        |.#..#.#.#..#....#..............
    """.trimMargin()

}
