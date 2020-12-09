package com.github.shmvanhouten.adventofcode2020.day09


fun findFirstNumberThatDoesNotAddUp(input: List<Long>, preambleSize: Int = 25): Long {
    return input[
            generateSequence(preambleSize + 1) { it + 1 }
                .first { doesNotAddUp(input[it], input.subList(it - preambleSize, it)) }
    ]
}

private fun doesNotAddUp(number: Long, subList: List<Long>): Boolean {
    return subList.none { addsUp(it, subList - it, number) }
}

private fun addsUp(firstNumber: Long, others: List<Long>, target: Long): Boolean {
    return others.contains(target - firstNumber)
}

fun findContiguousListThatAddsUpTo(target: Long, numbers: List<Long>): List<Long> {
    val startIndexOfContiguousSubSet = generateSequence(0) { it + 1 }
        .first { subListAddsUpTo(numbers.subList(it, numbers.size), target) }
    return listContiguousNumberSummingUpToTarget(numbers.subList(startIndexOfContiguousSubSet, numbers.size), target)
}

fun subListAddsUpTo(numbers: List<Long>, target: Long): Boolean {
    return listContiguousNumberSummingUpToTarget(numbers, target).sum() == target
}

private fun listContiguousNumberSummingUpToTarget(
    numbers: List<Long>,
    target: Long
) = numbers.subList(0,
    generateSequence(1) { it + 1 }
        .takeWhile { numbers.subList(0, it).sum() <= target }
        .last()
)
