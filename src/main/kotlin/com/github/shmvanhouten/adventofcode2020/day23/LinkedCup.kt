package com.github.shmvanhouten.adventofcode2020.day23

data class LinkedCup(val labelNumber: Int) {
    lateinit var nextCup: LinkedCup
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LinkedCup

        if (labelNumber != other.labelNumber) return false

        return true
    }

    override fun hashCode(): Int {
        return labelNumber
    }

}

fun initiateLinkedCups(cupOrder : List<Int>): Set<LinkedCup> {
    var currentCup: LinkedCup? = null
    var firstCup: LinkedCup? = null
    val cups = mutableSetOf<LinkedCup>()
    for (cupNumber in cupOrder) {
        val previousCup = currentCup
        currentCup = LinkedCup(cupNumber)
        if(previousCup != null) {
            previousCup.nextCup = currentCup
        } else {
            firstCup = currentCup
        }
        cups.add(currentCup)
    }
    currentCup!!.nextCup = firstCup!!
    return cups.toSet()
}