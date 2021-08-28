package com.github.shmvanhouten.adventofcode2020.day14

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.allElements
import com.natpryce.hamkrest.allOf
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day14Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `masking 11 results in 73`() {
            val program = """
                |mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                |mem[8] = 11
            """.trimMargin()
            val seaPortComputer = SeaPortComputer()
            seaPortComputer.initialize(program.lines())
            assertThat(seaPortComputer.sumMemory(), equalTo(73L))
        }

        @Test
        internal fun `masking 101 results in 101`() {
            val program = """
                |mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                |mem[8] = 101
            """.trimMargin()
            val seaPortComputer = SeaPortComputer()
            seaPortComputer.initialize(program.lines())
            assertThat(seaPortComputer.sumMemory(), equalTo(101L))
        }

        @Test
        internal fun `add em up`() {
            val program = """
                |mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                |mem[8] = 11
                |mem[7] = 101
                |mem[8] = 0
            """.trimMargin()
            val seaPortComputer = SeaPortComputer()
            seaPortComputer.initialize(program.lines())
            assertThat(seaPortComputer.sumMemory(), equalTo(165L))
        }

        @Test
        internal fun `part 1`() {
            val program = readFile("/input-day14.txt")
            val seaPortComputer = SeaPortComputer()
            seaPortComputer.initialize(program.lines())
            assertThat(seaPortComputer.sumMemory(), equalTo(12135523360904))
        }
    }

    @Nested
    inner class Part2 {

        @Test
        internal fun `test input`() {
            val program = """
                |mask = 000000000000000000000000000000X1001X
                |mem[42] = 100
                |mask = 00000000000000000000000000000000X0XX
                |mem[26] = 1
            """.trimMargin()
            val seaPortComputer = SeaPortComputerV2()
            seaPortComputer.initialize(program)
            assertThat(seaPortComputer.sumMemory(), equalTo(208L))
        }

        @Test
        internal fun `part 2`() {
            val program = readFile("/input-day14.txt")
            val seaPortComputer = SeaPortComputerV2()
            seaPortComputer.initialize(program)
            assertThat(seaPortComputer.sumMemory(), equalTo(2741969047858))
        }
    }

}
