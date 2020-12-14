package com.github.shmvanhouten.adventofcode2020.day14

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo


class SeaPortComputer (val memory: MutableMap<Int, Long> = mutableMapOf()) {

    fun initialize(program: List<String>) {
        var mask = 0L to 0L
        for (instruction in program) {
            val (type, value) = instruction.splitIntoTwo(" = ")
            if(type == "mask") {
                mask = extractOnesMask(value) to extractZerosMask(value)
            } else {
                val address = type.substring(type.indexOf('[') + 1, type.indexOf(']')).toInt()
                val maskedValue = value.toLong().or(mask.first).and(mask.second)
                memory[address] = maskedValue
            }
        }
    }

    private fun extractOnesMask(value: String) = value.map {
        when(it) {
            'X' -> '0'
            else -> it
        }
    }.joinToString("")
        .toLong(2)

    private fun extractZerosMask(value: String) = value.map {
        when(it) {
            'X' -> '1'
            else -> it
        }
    }.joinToString("")
        .toLong(2)

    fun sumMemory(): Long {
        return memory.values.sum()
    }


}