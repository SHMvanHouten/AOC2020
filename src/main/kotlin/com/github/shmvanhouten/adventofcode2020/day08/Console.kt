package com.github.shmvanhouten.adventofcode2020.day08

import java.lang.IllegalStateException

class Console(private val instructions: List<Instruction>) {
    private var pointer = 0
    private var accumulator = 0L
    fun accumulateUntilFirstRecurringInstruction(): Long {
        val visitedPositions = mutableSetOf<Int>()

        while (!visitedPositions.contains(pointer) && pointer < instructions.size) {
            visitedPositions.add(pointer)
            val instruction = instructions[pointer]
            println("pointer: $pointer")
            println("accumulator: $accumulator")
            println("instruction: ${instruction} ${instruction.argument}")
            if (instruction is Acc) {
                accumulator += instruction.argument
                pointer++
            } else if (instruction is Jmp) {
                pointer += instruction.argument.toInt()
            } else {
                pointer++
            }
        }

        if(pointer >= instructions.size) {
            throw IllegalStateException("something $accumulator")
        }

        return accumulator
    }
}