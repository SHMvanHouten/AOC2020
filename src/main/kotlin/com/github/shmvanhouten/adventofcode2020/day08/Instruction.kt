package com.github.shmvanhouten.adventofcode2020.day08

open class Instruction (val argument: Long)

class Nop(argument: Long) : Instruction(argument)
class Jmp(argument: Long) : Instruction(argument)
class Acc(argument: Long) : Instruction(argument)

fun parseInstructions(testInput: String): List<Instruction> {
    return testInput.lines()
        .map { toInstruction(it) }
}

fun toInstruction(raw: String): Instruction {
    val split = raw.split(' ')
            val argument = split[1].toLong()
    return when(split[0]) {
        "nop" -> Nop(argument)
        "acc" -> Acc(argument)
        "jmp" -> Jmp(argument)
        else -> throw IllegalArgumentException("unknown instruction: $raw")
    }
}
