package com.github.shmvanhouten.adventofcode2020.util

fun String.rotatedRight(): String {
    return (this.lines().first().lastIndex.downTo(0))
        .joinToString("\n") { charPosition ->
            (0..this.lines().lastIndex)
                .map { lineIndex -> this.lines()[lineIndex] }
                .map { line -> line[charPosition] }
                .joinToString("")
        }
}

fun String.rotatedInAllDirections(): List<String> {
    return listOf(
        this,
        this.rotatedRight(),
        this.rotatedRight().rotatedRight(),
        this.rotatedRight().rotatedRight().rotatedRight()
    )
}

fun String.flipped(): String {
    return this
        .lines()
        .joinToString("\n") { it.reversed() }
}