package com.github.shmvanhouten.adventofcode2020.day05

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day05Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `so the id is 44 * 8 + 5`() {
            assertThat(calculateSeatId("FBFBBFFRLR"), equalTo(44 * 8L + 5))
            assertThat(calculateSeatId("BFFFBBFRRR"), equalTo(70 * 8L + 7))
            assertThat(calculateSeatId("FFFBBBFRRR"), equalTo(14 * 8L + 7))
            assertThat(calculateSeatId("BBFFBBFRLL"), equalTo(102 * 8L + 4))
        }

        @Test
        internal fun `part 1`() {
            val input = readFile("/input-day05.txt")
            assertThat(listSeatNumbers(input).max(), equalTo(963L))
        }
    }

    @Test
    internal fun `part 2`() {
        val input = readFile("/input-day05.txt")
        val seatNrs = listSeatNumbers(input)
        assertThat(findMissingSeat(seatNrs), equalTo(592L))
    }

}
