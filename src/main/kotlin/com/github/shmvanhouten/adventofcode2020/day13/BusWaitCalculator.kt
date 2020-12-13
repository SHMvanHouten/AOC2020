package com.github.shmvanhouten.adventofcode2020.day13

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

fun calculateShortestWait(input: String): Pair<Int, Int>? {
    val (departureTime, busIDsString) = input.splitIntoTwo("\n")
    return busIDsString.split(',')
        .filter { it != "x" }
        .map { it.toInt() }
        .map { it to calculateWaitTime(departureTime.toInt(), it) }
        .minBy { it.second }
}

private fun calculateWaitTime(departureTime: Int, busTime: Int): Int {

    return busTime - (departureTime % busTime)
}