package com.github.shmvanhouten.adventofcode2020.day05

import com.github.shmvanhouten.adventofcode2019.util.splitIntoTwo

fun listSeatNumbers(input: String) = input.lines().map { calculateSeatId(it) }

fun calculateSeatId(input: String): Long {
    val (row, column) = input.splitIntoTwo(7)
    return row.flbrBinaryToInt() * 8L + column.flbrBinaryToInt()
}

fun findMissingSeat(seatNrs: List<Long>): Long {
    return (0L..seatNrs.max()!!)
        .filter { !seatNrs.contains(it) }
        .first { seatNrs.containsAll(listOf(it - 1, it + 1)) }
}

private fun String.flbrBinaryToInt(): Int = this
    .replace(Regex("[FL]"), "0")
    .replace(Regex("[BR]"), "1")
    .binaryToInt()

private fun String.binaryToInt(): Int = Integer.parseInt(this, 2)

