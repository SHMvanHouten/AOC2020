package com.github.shmvanhouten.adventofcode2020.day13

import com.github.shmvanhouten.adventofcode2019.primefactor.primeFactors
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day13Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `example 1`() {
            val shortestWaitByBusID = calculateShortestWait(testInput)

            assertThat(shortestWaitByBusID, equalTo(59 to 5))
            assertThat(shortestWaitByBusID?.first?.times(shortestWaitByBusID.second), equalTo(295))
        }


        @Test
        internal fun `part 1`() {
            val shortestWaitByBusID = calculateShortestWait(input)

            assertThat(shortestWaitByBusID, equalTo(373 to 6))
            assertThat(shortestWaitByBusID?.first?.times(shortestWaitByBusID.second), equalTo(2238))
        }
    }

    companion object {
        //todo: make work with nested class?
        @JvmStatic
        fun sources(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("1,2,3", 1L),
                Arguments.of("2,3,4", 2L),
                Arguments.of("3,2,7", 33L),
                Arguments.of("3,2,7,13", 75L),
                Arguments.of("3,2,7,11", 327L),
                Arguments.of("7", 7L),
                Arguments.of("7,13", 77L),
                Arguments.of("7,13,23", 987L),
                Arguments.of("7,13,23,19", 26103L),
                Arguments.of("17,x,13,19", 3417L),
                Arguments.of("67,7,59,61", 754018L),
                Arguments.of("67,x,7,59,61", 779210L),
                Arguments.of("67,7,x,59,61", 1261476L),
                Arguments.of("1789,37,47,1889", 1202161486L),
                Arguments.of("7,13,x,x,59,x,31,19", 1068781L),
                Arguments.of("19,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,367,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,373,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23", 560214575859998L)
            )

        }
    }

    @ParameterizedTest(name = "first time schedule {0} works is {1}")
    @MethodSource("sources")
    internal fun `first time schedule works brute force`(
        input: String,
        expected: Long
    ) {
        val busIDsByOffset = parse(input)
        println(busIDsByOffset.map { it.id }.map { primeFactors(it.toLong()) }.joinToString(", "))
        println(busIDsByOffset.map { primeFactors(it.offset.toLong() + it.id) }
            .joinToString(", "))
        if(expected < 26103L) {
            assertThat(
                findEarliestTimestampWhereBusesComeInAtThePrescribedTimesBruteForce(busIDsByOffset),
                equalTo(expected)
            )
        }
    }

    @ParameterizedTest(name = "first time schedule {0} works is {1}")
    @MethodSource("sources")
    internal fun `first time schedule works`(
        input: String,
        expected: Long
    ) {
        assertThat(
            findEarliestTimestampWhereBusesComeInAtThePrescribedTimes(parse(input)),
            equalTo(expected)
        )

    }

    @Test
    internal fun `part 2`() {
        assertThat(
            findEarliestTimestampWhereBusesComeInAtThePrescribedTimes(parse(input)),
            equalTo(560214575859998L)
        )
    }

    @Test
    internal fun `multiples of 3, 2, 7`() {
        (0..25).forEach{
            val multipleOf3 = it * 3
            println("$multipleOf3 , ${(multipleOf3 + 1) % 2} , ${(multipleOf3 + 2) % 7}, ${(multipleOf3 + 3) % 11}")
        }
    }
}

private fun parse(input: String): List<Bus> {
    return input.lines().last()
        .split(',')
        .mapIndexed {i, busID ->
            if (busID == "x") null
            else Bus(i, busID.toInt())
        }.filterNotNull()
}

private val testInput = """939
7,13,x,x,59,x,31,19"""

private val input = """1002618
19,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,367,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,373,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23"""
