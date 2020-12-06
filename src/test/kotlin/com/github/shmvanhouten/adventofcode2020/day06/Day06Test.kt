package com.github.shmvanhouten.adventofcode2020.day06

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day06Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `one person answering a counts as 1`() {
            val input = "a"
            assertThat(sumGroupYesses(input), equalTo(1L))
        }

        @Test
        internal fun `one person answering ab counts as 2`() {
            val input = "ab"
            assertThat(sumGroupYesses(input), equalTo(2L))
        }

        @Test
        internal fun `two people answering a counts as 1`() {
            val input = """a
                |a
            """.trimMargin()
            assertThat(sumGroupYesses(input), equalTo(1L))
        }

        @Test
        internal fun `two people in two different groups answering a counts as 2`() {
            val input = """a
                |
                |a
            """.trimMargin()
            assertThat(sumGroupYesses(input), equalTo(2L))
        }

        @Test
        internal fun `test input`() {
            val input = """
                abc
                
                a
                b
                c
                
                ab
                ac
                
                a
                a
                a
                a
                
                b
                """.trimIndent()
            assertThat(sumGroupYesses(input), equalTo(11L))
        }

        @Test
        internal fun `part 1`() {
            val input = readFile("/input-day06.txt")
            assertThat(sumGroupYesses(input), equalTo(6680L))
        }
    }

    @Nested
    inner class Part2 {

        @Test
        internal fun `test input`() {
            val input = """
                abc
                
                a
                b
                c
                
                ab
                ac
                
                a
                a
                a
                a
                
                b
                """.trimIndent()
            assertThat(sumSharedYesses(input), equalTo(6))
        }

        @Test
        internal fun `part 2`() {
            val input = readFile("/input-day06.txt")
            assertThat(sumSharedYesses(input), equalTo(3117))
        }
    }

}
