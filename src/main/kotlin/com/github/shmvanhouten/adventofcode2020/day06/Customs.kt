package com.github.shmvanhouten.adventofcode2020.day06

fun sumGroupYesses(input: String): Long {
    return input
        .blocks()
        .map { countUniqueAnswers(it) }
        .sum()
}

fun sumSharedYesses(input: String) = input
    .blocks()
    .map { countSharedAnswers(it) }
    .sum()

private fun String.blocks(): List<String> = this.split("\n\n")

private fun countUniqueAnswers(input: String) = input.replace("\n", "").toSet().size.toLong()

private fun countSharedAnswers(groupAnswers: String): Int {
    return groupAnswers.lines()
        .reduce{ a, b -> a.filter { b.contains(it) } }
        .length
}

