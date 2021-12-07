package com.github.shmvanhouten.adventofcode2020.day08

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day08Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `nop then jmp -1 executes the same instruction the second time when accumulator is 0 `() {
            val instructions = listOf(
                Nop(0L),
                Jmp(-1L)
            )
            val console = Console(instructions)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(0L) )
        }

        @Test
        internal fun `1 nops then acc 1 then jmp -2 executes the same instruction the second time when accumulator is 1 `() {
            val instructions = listOf(
                Nop(0L),
                Acc(1L),
                Jmp(-2L)
            )
            val console = Console(instructions)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(1L) )
        }

        @Test
        internal fun `jmp 2 then jmp -2 with a nop and acc 1 in between accumulates to 1 before repeating`() {
            val instructions = listOf(
                Jmp(2),
                Nop(0),
                Acc(1),
                Jmp(-2)
            )
            val console = Console(instructions)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(1L))
        }

        @Test
        internal fun `nop then acc 2 then jmp -2 executes the same instruction the second time when accumulator is 2 `() {
            val instructions = listOf(
                Nop(0L),
                Acc(2L),
                Jmp(-2L)
            )
            val console = Console(instructions)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(2L) )
        }

        @Test
        internal fun `acc 1 then acc 1 then jump -1 accumulates to 2`() {
            val instructions = listOf(
                Acc(1L),
                Acc(1L),
                Jmp(-1L)
            )
            val console = Console(instructions)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(2L) )
        }

        @Test
        internal fun `jump around until we repeat on accumulator value 2`() {
            val instructions = listOf(
                Jmp(2L),
                Acc(1L),
                Acc(1L),
                Jmp(2L),
                Acc(4L),
                Jmp(-4L)
            )
            val console = Console(instructions)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(2L) )
        }

        @Test
        internal fun `test input (parse)`() {
            val input = parseInstructions(testInput)
            val console = Console(input)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(5L) )
        }

        @Test
        internal fun `part 1`() {
            val input = parseInstructions(readFile("/input-day08.txt"))
            val console = Console(input)
            assertThat(console.accumulateUntilFirstRecurringInstruction(), equalTo(1949L) )
        }
    }

    @Nested
    inner class Part2 {

        @Test
        @Disabled("is meant to fail, the failing accumulator is the answer")
        internal fun `part 2`() {
            val instructions = parseInstructions(readFile("/input-day08.txt"))
            val positionsOfNopsAndJmps = instructions
                .mapIndexed { index, instruction ->  index to instruction }
                .filter { (_, instruction) -> instruction is Jmp || instruction is Nop }

            positionsOfNopsAndJmps.forEach { (pos, instruction) ->
                val mutableInstructions = instructions.toMutableList()
                mutableInstructions[pos] = instruction.flip()
                val console = Console(mutableInstructions)
                console.accumulateUntilFirstRecurringInstruction()
            }
        }

    }
}

private fun Instruction.flip(): Instruction {
    return when(this) {
        is Jmp -> Nop(this.argument)
        is Nop -> Jmp(this.argument)

        else -> throw IllegalArgumentException("can't flip instruction $this")
    }
}

val testInput = """nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6"""
