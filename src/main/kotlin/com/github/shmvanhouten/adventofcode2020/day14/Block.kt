package com.github.shmvanhouten.adventofcode2020.day14

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

data class Block(private val mask: String, val instructions: List<Instruction>) {
    val baseMask: Long = filterOutBaseMask()

    private val floats = filterOutFloats()

    fun permuteMaskXsTo0sAnd1s(): List<Mask> {
        return permuteXsTo0sAnd1s(floats)
    }

    private fun permuteXsTo0sAnd1s(
        floaters: List<Int>,
        mask: Mask = Mask()
    ): List<Mask> {
        if (floaters.isEmpty()) return listOf(mask)
        return permuteXsTo0sAnd1s(
            floaters.subList(1, floaters.size),
            mask.copy(to0Mask = StringBuilder(mask.to0Mask).also { it.setCharAt(floaters[0], '0') }.toString())
        ) + permuteXsTo0sAnd1s(
            floaters.subList(1, floaters.size),
            mask.copy(to1Mask = StringBuilder(mask.to1Mask).also { it.setCharAt(floaters[0], '1') }.toString())
        )
    }

    private fun filterOutBaseMask() = mask
        .map {
            when (it) {
                'X' -> 0
                else -> it
            }
        }.joinToString("").toLong(2)

    private fun filterOutFloats() = mask
        .mapIndexedNotNull { i, c ->
            when (c) {
                'X' -> i
                else -> null
            }
        }
}

data class Mask(val to0Mask: String = "1".repeat(36), val to1Mask: String = "0".repeat(36))

typealias Address = Long

data class Instruction(val address: Address, val value: Long)

fun toBlock(raw: String): Block {
    val (mask, rawInstructions) = raw.lines().filter { it.isNotEmpty() }.splitIntoTwo(1)
    return Block(mask[0].split(" = ")[1], rawInstructions.map { toInstruction(it) })
}

fun toInstruction(raw: String): Instruction {
    val (type, value) = raw.splitIntoTwo(" = ")
    val address = type.substring(type.indexOf('[') + 1, type.indexOf(']')).toLong()
    return Instruction(address, value.toLong())
}
