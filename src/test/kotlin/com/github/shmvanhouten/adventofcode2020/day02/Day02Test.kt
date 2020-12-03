package com.github.shmvanhouten.adventofcode2020.day02

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day02Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `1-3 a =-= a is valid`() {
            assertThat(countValidPasswords(listOf("1-3 a : a")), equalTo(1) )
        }

        @Test
        internal fun `1-3 a =-= b is invalid`() {
            assertThat(countValidPasswords(listOf("1-3 a : b")), equalTo(0))
        }

        @Test
        internal fun `1-3 b =-= b is valid`() {
            assertThat(countValidPasswords(listOf("1-3 b : b")), equalTo(1))
        }

        @Test
        internal fun `1-3 b =-= ab is valid`() {
            assertThat(countValidPasswords(listOf("1-3 b : ab")), equalTo(1))
        }

        @Test
        internal fun `2-3 a =-= a is invalid`() {
            assertThat(countValidPasswords(listOf("2-3 a : a")), equalTo(0))
        }

        @Test
        internal fun `1-2 a =-= aaa is invalid`() {
            assertThat(countValidPasswords(listOf("1-2 a : aaa")), equalTo(0))
        }

        @Test
        internal fun `part 1`() {
            assertThat(countValidPasswords(splitOnEndlines()), equalTo(458))
        }
    }

    @Nested
    inner class Part2 {

        @Test
        internal fun `1-2 a =-= aa is invalid`() {
            assertThat(countValidPasswords2(listOf("1-2 a : aa")), equalTo(0))
        }

        @Test
        internal fun `1-2 a =-= bb is invalid`() {
            assertThat(countValidPasswords2(listOf("1-2 a : bb")), equalTo(0))
        }

        @Test
        internal fun `1-2 b =-= bb is invalid`() {
            assertThat(countValidPasswords2(listOf("1-2 b : bb")), equalTo(0))
        }

        @Test
        internal fun `1-2 a =-= bba is invalid`() {
            assertThat(countValidPasswords2(listOf("1-2 a : bba")), equalTo(0))
        }

        @Test
        internal fun `1-2 a =-= abb is valid`() {
            assertThat(countValidPasswords2(listOf("1-2 a : abb")), equalTo(1))
        }

        @Test
        internal fun `1-2 a =-= bab is valid`() {
            assertThat(countValidPasswords2(listOf("1-2 a : bab")), equalTo(1))
        }


        @Test
        internal fun `1-2 a =-= aab is invalid`() {
            assertThat(countValidPasswords2(listOf("1-2 a : aab")), equalTo(0))
        }

        @Test
        internal fun `part 2`() {
            assertThat(countValidPasswords2(splitOnEndlines()), equalTo(342))
        }
    }

    private fun splitOnEndlines(): List<String> {
        return readFile("/input-day02.txt").split('\n')
    }

}
