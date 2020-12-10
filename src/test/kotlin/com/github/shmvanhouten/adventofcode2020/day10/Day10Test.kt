package com.github.shmvanhouten.adventofcode2020.day10

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day10Test {

    @Nested
    inner class Part1 {

        @ParameterizedTest
        @CsvSource(
            "1, 1",
            "1|2, 2",
            "1|2|5, 4",
            "1|4|5, 4",
            "16|10|15|5|1|11|7|19|6|12|4,35",
            "28|33|18|42|31|14|46|20|48|47|24|23|49|45|19|38|39|11|1|32|25|35|8|17|7|9|4|2|34|10|3,220"

        )
        internal fun `the number of 1 jolt differences multiplied by the number of 3 jolt differences`(
            input: String,
            expected: Long
        ) {

            val inputOutlets = input.map {
                if(it == '|')  '\n'
                else it
            }.joinToString("")
            assertThat(multiply1JoltStepsBy3JoltSteps(parse(inputOutlets)), equalTo(expected) )
        }

        @Test
        internal fun `part 1`() {
            val input = readFile("/input-day10.txt")
            assertThat(multiply1JoltStepsBy3JoltSteps(parse(input)), equalTo(3000L))
        }
    }

    @Nested
    inner class Part2 {

        @ParameterizedTest
        @CsvSource(
            "16|10|15|5|1|11|7|19|6|12|4,8",
            "28|33|18|42|31|14|46|20|48|47|24|23|49|45|19|38|39|11|1|32|25|35|8|17|7|9|4|2|34|10|3,19208"

        )
        internal fun `the number of possible arrangements`(
            input: String,
            expected: Long
        ) {

            val inputOutlets = input.map {
                if(it == '|')  '\n'
                else it
            }.joinToString("")
            assertThat(countPossibleArrangements(parse(inputOutlets)), equalTo(expected) )
        }

        @Test
        internal fun `part 2`() {
            val input = readFile("/input-day10.txt")
            assertThat(countPossibleArrangements(parse(input)), equalTo(193434623148032L))
        }
    }

    private fun parse(input: String): List<Long> {
        return input.lines().map { it.toLong() }
    }

}
//15 -> 1
//12 -> 1
//11 -> 1
//10 -> 1(11) + 1(12)
//7 -> 2 (10)
//6 -> 2 (7)
//5 -> 2(6) + 2(7)
//4 -> 4(5) + 2(6) + 2(7)
//1 -> 8(4)
//0 -> 8(1)
