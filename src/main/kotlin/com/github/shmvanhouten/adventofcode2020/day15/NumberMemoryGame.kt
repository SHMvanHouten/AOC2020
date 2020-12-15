package com.github.shmvanhouten.adventofcode2020.day15

fun findSpokenNumberAt(numberSpoken: Int, input: List<Int>): Int {
    val previousNumbersSpokenByIndex = input
        .subList(0, input.lastIndex)
        .mapIndexed{i, nr -> nr to i}
        .toMap().toMutableMap()
    var lastSpoken = input.last()
    previousNumbersSpokenByIndex.size.until(numberSpoken - 1).forEach{ currentNumberSpoken ->
        val newLastSpoken = previousNumbersSpokenByIndex[lastSpoken]
            ?. differenceBetween(currentNumberSpoken)
            ?: 0

        previousNumbersSpokenByIndex[lastSpoken] = currentNumberSpoken

        lastSpoken = newLastSpoken
    }
    return lastSpoken
}

private fun Int.differenceBetween(other: Int): Int {
    return other - this
}
