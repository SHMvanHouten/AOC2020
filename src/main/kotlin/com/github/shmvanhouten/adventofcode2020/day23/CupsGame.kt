package com.github.shmvanhouten.adventofcode2020.day23

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

fun act(cupsGame: CupsGame, times: Int): CupsGame {
    var i = 0
    var game = cupsGame
    while (i < times) {
        game = game.act()
        i += 1
    }
    return game
}

class CupsGame(private val cups: List<Cup>) {

    constructor(cupsLabeling: String) : this(cupsLabeling.map { it.toString().toInt() })

    fun act(): CupsGame {
        val (pickedUpCups, remainingCups) = pickUp3CupsNextToCurrent()
        val updatedCups = insertPickedUpCupsAtIndex(remainingCups, pickedUpCups, findIndexOfTargetCup(remainingCups))
        return CupsGame(updatedCups.moveCupsSoNewTargetCupIsAtStart())
    }

    private fun insertPickedUpCupsAtIndex(
        cups: List<Cup>,
        pickedUpCups: List<Cup>,
        targetCupIndex: Int
    ): List<Cup> {
        val (cupsBefore, cupsAfter) = cups.splitIntoTwo(targetCupIndex + 1)
        return cupsBefore + pickedUpCups + cupsAfter
    }

    private fun findIndexOfTargetCup(remainingCups: List<Cup>): Int {
        var targetCupValue = remainingCups.first() - 1

        while (remainingCups.indexOf(targetCupValue) == -1) {
            if(targetCupValue == 0) {
                targetCupValue = 9
            } else {
                targetCupValue -= 1
            }
        }

        return remainingCups.indexOf(targetCupValue)
    }

    private fun pickUp3CupsNextToCurrent(): Pair<List<Cup>, List<Cup>> {
        return cups.subList(1,4) to cups.first() + cups.subList(4, cups.size)
    }

    fun printCups(): String {
        return cups.joinToString("")
    }

    fun printCupsAfter1(): String {
        return (cups.subList(cups.indexOf(1) + 1, cups.size) + cups.subList(0, cups.indexOf(1)))
            .joinToString("")
    }

}

private fun List<Cup>.moveCupsSoNewTargetCupIsAtStart(): List<Cup> {
    return this.subList(1, this.size) + this.first()
}

private operator fun Int.plus(list: List<Cup>): List<Cup> {
    return listOf(this) + list
}

typealias Cup = Int