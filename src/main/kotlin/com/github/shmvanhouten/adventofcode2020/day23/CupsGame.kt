package com.github.shmvanhouten.adventofcode2020.day23

fun act(cupsGame: CupsGame, times: Int): CupsGame {
    repeat(times) {cupsGame.act()}
    return cupsGame
}

class CupsGame(private val cupsOrder: List<Int>) {
    val cups: Map<Int, LinkedCup> = initiateLinkedCups(cupsOrder).associateBy { it.labelNumber }
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
        val the3Cups = mutableListOf(currentCup.nextCup)
        for (i in 0.until(2)) {
            the3Cups.add(the3Cups.last().nextCup)
        }
        currentCup.nextCup = the3Cups.last().nextCup
        return the3Cups.toList()
    }

    private fun findTargetCup(pickedUpCups: List<LinkedCup>): LinkedCup {
        val targetCupNumber = (currentCup.labelNumber - 1)
            .downTo(1)
            .repeat()
            .first { nr -> pickedUpCups.none { it.labelNumber == nr } }

        return cups[targetCupNumber]!!
    }

    fun printCups(): String {
        val stringBuilder = StringBuilder(currentCup.labelNumber.toString())
        var cup = currentCup.nextCup
        while (cup != currentCup) {
            stringBuilder.append(cup.labelNumber)
            cup = cup.nextCup
        }
        return stringBuilder.toString()
    }

    fun printCupsAfter1(): String {
        val stringBuilder = StringBuilder()
        val cup1 = cups[1]!!
        var cup = cup1.nextCup
        while (cup != cup1) {
            stringBuilder.append(cup.labelNumber)
            cup = cup.nextCup
        }
        return stringBuilder.toString()
    }

    fun get2CupsAfter1(): Pair<Int, Int> {
        val cup1 = cups[1]!!
        val nextCup = cup1.nextCup
        return nextCup.labelNumber to nextCup.nextCup.labelNumber
    }
}

fun <T> Sequence<T>.repeat(): Sequence<T> =
    generateSequence(this) { this }.flatten()

fun IntProgression.repeat(): Sequence<Int> =
    this.asSequence().repeat()
