package com.github.shmvanhouten.adventofcode2020.day15

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day15Test {

    @Nested
    inner class Part1 {

        @ParameterizedTest
        @CsvSource(
            "4,0",
            "5,3",
            "6,3",
            "7,1",
            "8,0",
            "9,4",
            "10,0"
        )
        internal fun `given starting numbers 0,3,6 the next numbers spoken are`(
            numberSpoken: Int,
            expected: Int
        ) {
            val input = listOf(0,3,6)
            assertThat(findSpokenNumberAt(numberSpoken, input), equalTo(expected))
        }

        @ParameterizedTest
        @CsvSource(
            "1,3,2:1",
            "2,1,3:10",
            "1,2,3:27",
            "2,3,1:78",
            "3,2,1:438",
            "3,1,2:1836",
            delimiter = ':'
        )
        internal fun examples(input: String, expected: Int) {
            val startingNumbers = input.split(',').map { it.toInt() }
            assertThat(findSpokenNumberAt(2020, startingNumbers), equalTo(expected))
        }

        @Test
        internal fun `part 1`() {
            val startingNumbers = input
            assertThat(findSpokenNumberAt(2020, startingNumbers), equalTo(253))
        }
    }

    @Test
    internal fun `part 2`() {
        val startingNumbers = input
//         6 seconds
//        assertThat(findSpokenNumberAt(30000000, startingNumbers), equalTo(13710))
    }

}

val input = listOf(18, 8, 0, 5, 4, 1, 20)
