package com.github.shmvanhouten.adventofcode2020.day23

import com.github.shmvanhouten.adventofcode2020.util.repeat

fun act(cupsGame: CupsGame, times: Int): CupsGame {
    repeat(times) {cupsGame.act()}
    return cupsGame
}

class CupsGame(cupsOrder: List<Int>) {
    private val cups: Map<Int, LinkedCup> = initiateLinkedCups(cupsOrder).associateBy { it.labelNumber }
    var currentCup: LinkedCup = cups[cupsOrder.first()]!!

    fun act() {
        val pickedUpCups = pickUp3CupsNextToCurrent()
        insertPickedUpCupsAtIndex(findTargetCup(pickedUpCups), pickedUpCups)

        currentCup = currentCup.nextCup
    }

    private fun insertPickedUpCupsAtIndex(targetCup: LinkedCup, pickedUpCups: List<LinkedCup>) {
        val rightCup = targetCup.nextCup
        targetCup.nextCup = pickedUpCups.first()
        pickedUpCups.last().nextCup = rightCup
    }

    private fun pickUp3CupsNextToCurrent(): List<LinkedCup> {
        val pickedUpCups = currentCup.nextCup.take(3)
        currentCup.nextCup = pickedUpCups.last().nextCup
        return pickedUpCups
    }

    private fun findTargetCup(pickedUpCups: List<LinkedCup>): LinkedCup {
        val targetCupNumber = (currentCup.labelNumber - 1)
            .downTo(1)
            .repeat()
            .first { nr -> pickedUpCups.none { it.labelNumber == nr } }

        return cups[targetCupNumber]!!
    }

    fun printCups(): String {
        return currentCup.takeWhile { it != currentCup }
            .map { it.labelNumber }
            .joinToString("")
    }

    fun printCupsAfter1(): String {
        val cup1 = cups[1]!!
        return cup1.takeWhile { it != cup1 }
            .joinToString("")
    }

    fun get2CupsAfter1(): List<LinkedCup> {
        return cups[1]!!.nextCup.take(2)
    }
}
