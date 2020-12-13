package com.github.shmvanhouten.adventofcode2020.day13

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

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

    @Nested
    inner class Part2 {

    }
}

val testInput = """939
7,13,x,x,59,x,31,19"""

val input = """1002618
19,x,x,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,367,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,29,x,373,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,23"""
