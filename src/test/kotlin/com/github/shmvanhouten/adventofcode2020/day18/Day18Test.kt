package com.github.shmvanhouten.adventofcode2020.day18

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day18Test {

    @Nested
    inner class Part1 {

        @ParameterizedTest
        @CsvSource(
            "1 + 2, 3",
            "1 + 1, 2",
            "2 + 2, 4",
        )
        internal fun `evaluate + as +`(
            sum: String,
            expectedResult: Long
        ) {
            assertThat(evaluate(sum), equalTo(expectedResult) )
        }

        @ParameterizedTest
        @CsvSource(
            "1 * 1, 1",
            "1 * 2, 2",
            "2 * 2, 4",
        )
        internal fun `evaluate * as *`(
            sum: String,
            expectedResult: Long
        ) {
            assertThat(evaluate(sum), equalTo(expectedResult) )
        }

        @ParameterizedTest
        @CsvSource(
            "1 + 1 * 2, 4",
            "2 + 1 * 2, 6",
            "2 * 1 + 1, 3",
        )
        internal fun `operators are evaluated left to right`(
            sum: String,
            expectedResult: Long
        ) {
            assertThat(evaluate(sum), equalTo(expectedResult) )
        }

        @ParameterizedTest
        @CsvSource(
            "(1 + 1) * 2, 4",
            "(2 + 1) * 2, 6",
            "7 * (2 + 2 * 3 + (9 + 3 + 7 * 3)), 483",
            "2 * 3 + (4 * 5), 26",
            "5 + (8 * 3 + 9 + 3 * 4 * 3), 437",
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)), 12240",
            "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4)) + 1, 12241",
            "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2, 13632",
        )
        internal fun `bracket blocks are evaluated first`(
            sum: String,
            expectedResult: Long
        ) {
            assertThat(evaluate(sum), equalTo(expectedResult) )
        }

        @Test
        internal fun `part 1`() {
            assertThat(
                readFile("/input-day18.txt").lines()
                    .map { evaluate(it) }
                    .sum(),
                equalTo(1402255785165)
            )
        }
    }

}
