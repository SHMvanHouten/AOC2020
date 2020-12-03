package com.github.shmvanhouten.adventofcode2020.day01


private const val TARGET = 2020

fun find2020Sum(entries: List<Int>): Int {
    val sortedEntries: List<Int> = entries.sorted()

    return findPairThatAddsUpToTarget(sortedEntries, TARGET)
        .multiply();

}

fun find2020SumOfThree(entries: List<Int>): Int {
    val pair = entries.asSequence()
        .map {
            val others = entries - it
            val addsUpToTarget = findPairThatAddsUpToTarget(others.sorted(), TARGET - it)
            it to addsUpToTarget
        }
        .first { it.first + it.second.first + it.second.second == TARGET  }
    return pair.first * pair.second.multiply()
}


private fun findPairThatAddsUpToTarget(sortedEntries: List<Int>, target: Int): Pair<Int, Int> {
    return sortedEntries.asSequence().mapIndexed { i, entry ->
        val subList = sortedEntries.subList(i + 1, sortedEntries.size);
        val candidate = subList.reversed()
            .dropWhile { entry + it > target }
            .firstOrNull() ?: 2020
        (candidate to entry)
    }
        .toList()
        .find { (a, b) -> a + b == target } ?: 0 to 0
}

private fun Pair<Int, Int>.multiply(): Int {
    return first * second
}


