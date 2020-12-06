package com.github.shmvanhouten.adventofcode2020.day06

fun sumGroupYesses(input: String): Long {
    return input.split("\n\n")
        .map { countUniqueAnswers(it) }
        .sum()
}

fun sumSharedYesses(input: String) = input.split("\n\n")
    .map { countSharedAnswers(it) }
    .sum()

private fun countUniqueAnswers(input: String) = input.replace("\n", "").toSet().size.toLong()

private fun countSharedAnswers(groupAnswers: String): Int {
    return groupAnswers.lines()
        .reduce{ sa, sb -> sa.filter { sb.contains(it) } }
        .length
}

