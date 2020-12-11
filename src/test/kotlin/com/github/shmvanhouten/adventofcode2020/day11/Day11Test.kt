package com.github.shmvanhouten.adventofcode2020.day11

import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day11Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `empty seat surrounded by no occupied seats will tick to occupied`() {
            val input = """
                ...
                .L.
                ...
            """.trimIndent()
            val seats = parseEmptySeats(input)
            var seatingArea = SeatingArea(seats)
            seatingArea = seatingArea.tick()
            assertThat(seatingArea.occupiedSeats.size, equalTo(1))
            assertThat(seatingArea.emptySeats.size, equalTo(0))
        }

        @Test
        internal fun `one occupied seat means it remains occupied`() {
            val input = """
                ...
                .#.
                ...
            """.trimIndent()
            val seats = parseEmptySeats(input)
            val occupiedSeats = parseOccupiedSeats(input)
            var seatingArea = SeatingArea(seats, occupiedSeats)
            seatingArea = seatingArea.tick()
            assertThat(seatingArea.occupiedSeats.size, equalTo(1))
            assertThat(seatingArea.emptySeats.size, equalTo(0))
        }

        @Test
        internal fun `5 occupied seat in a star configuratioin mean the middle one becomes unoccupied`() {
            val input = """
                .#.
                ###
                .#.
            """.trimIndent()
            val seats = parseEmptySeats(input)
            val occupiedSeats = parseOccupiedSeats(input)
            var seatingArea = SeatingArea(seats, occupiedSeats)
            seatingArea = seatingArea.tick()
            assertThat(seatingArea.occupiedSeats.size, equalTo(4))
            assertThat(seatingArea.emptySeats.size, equalTo(1))
            assertThat(seatingArea.emptySeats.first(), equalTo(Coordinate(1,1)))
        }

        @Test
        internal fun testInput() {
            val input = """L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL"""
            val seatingArea = SeatingArea(parseEmptySeats(input))
            assertThat(tickUntilStable(seatingArea).occupiedSeats.size, equalTo(37))
        }

        @Test
        internal fun `part 1`() {
            val input = readFile("/input-day11.txt")
            val seatingArea = SeatingArea(parseEmptySeats(input))
            assertThat(tickUntilStable(seatingArea).occupiedSeats.size, equalTo(2470))
        }
    }

    @Nested
    inner class Part2 {

        @Test
        internal fun testInput() {
            val input = """L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL"""
            val seatingArea = SeatingArea(parseEmptySeats(input))
            assertThat(tickUntilStable2(seatingArea).occupiedSeats.size, equalTo(26))
        }
    }

}

fun parseEmptySeats(input: String): Set<Coordinate> {
    return input.split('\n').mapIndexed{i, row -> parseRow(i, row, 'L')}.flatten().toSet()
}

fun parseOccupiedSeats(input: String): Set<Coordinate> {
    return input.split('\n').mapIndexed{i, row -> parseRow(i, row, '#')}.flatten().toSet()
}

fun parseRow(y: Int, row: String, targetChar: Char): List<Coordinate> {
    return row
        .mapIndexed { x, c -> x to c }
        .filter { (_, c) -> c == targetChar }
        .map { (x, _) -> Coordinate(x,y) }
}
