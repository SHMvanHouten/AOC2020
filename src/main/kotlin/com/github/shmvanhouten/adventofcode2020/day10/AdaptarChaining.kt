package com.github.shmvanhouten.adventofcode2020.day10

fun multiply1JoltStepsBy3JoltSteps(outlets: List<Long>): Long {
    val sortedOutlets = (listOf(0L) + outlets).sorted()
    val jumpCounts = sortedOutlets
        .mapIndexed { index, outlet ->
            calculateJoltDifferenceWithNextOutlet(outlet, index, sortedOutlets)
        }
        .groupingBy { it }
        .eachCount()

    return 1L * (jumpCounts[1] ?: 0) * (jumpCounts[3] ?: 0)
}

private fun calculateJoltDifferenceWithNextOutlet(outlet: Long, index: Int, sortedOutlets: List<Long>) =
    if (index + 1 == sortedOutlets.size) {
        3
    } else {
        sortedOutlets[index + 1] - outlet
    }

fun countPossibleArrangements(outlets: List<Long>): Long {
    val possibleRoutesByOutlet = mutableMapOf(outlets.max()!! + 3L to 1L)
    (outlets + 0L).sorted().reversed()
        .forEach { outlet ->
            possibleRoutesByOutlet[outlet] = (1..3)
                .mapNotNull { possibleRoutesByOutlet[outlet + it] }
                .sum()
        }
    return possibleRoutesByOutlet[0]!!
}
