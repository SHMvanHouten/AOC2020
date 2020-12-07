package com.github.shmvanhouten.adventofcode2020.day05

fun listSeatNumbers(input: String) = input.lines().map { calculateSeatId(it) }

fun calculateSeatId(input: String): Long {
    return input.flbrBinaryToInt().toLong()
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

