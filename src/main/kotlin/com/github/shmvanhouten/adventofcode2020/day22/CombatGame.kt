package com.github.shmvanhouten.adventofcode2020.day22

fun play(game: CombatGame): CombatResult {
    return generateSequence(game) { it.playRound() }
        .first { it.hasAWinner() }
        .let { calculateResult(it) }
}

fun calculateResult(combatGame: CombatGame): CombatResult {
    return combatGame.winningPlayer().result()
}

class CombatGame(val player1: Player, val player2: Player) {

    fun playRound(): CombatGame {
        return if (player1.nextCard() > player2.nextCard()) {
            player1WinsRound()
        } else {
            player2WinsRound()
        }
    }

    fun hasAWinner(): Boolean {
        return player1.hasWon() || player2.hasWon()
    }

    private fun hasNoWinner(): Boolean {
        return !hasAWinner()
    }

    private fun player1WinsRound(): CombatGame {
        return CombatGame(
            player1.winsRound(player2.nextCard()),
            player2.losesRound()
        )
    }

    private fun player2WinsRound(): CombatGame {
        return CombatGame(
            player2.winsRound(player1.nextCard()),
            player1.losesRound()
        )
    }

    fun winningPlayer(): Player {
        return if(hasNoWinner()) {
            throw Error("No winner could be determined!")
        } else {
            if(player1.cards.isNotEmpty()) {
                player1
            } else {
                player2
            }
        }
    }

}

data class CombatResult(val winner: String, val cards: List<Int>) {
    val score: Long
        get() {
            return this.cards.reversed().mapIndexed { i, card ->
                (i + 1L) * card
            }.sum()
        }
}

data class Player(val cards: List<Int>, val name: String) {
    fun nextCard(): Int {
        return cards.first()
    }

    fun winsRound(losingCard: Int): Player {
        return this.copy(
            cards = cards.subList(1, cards.size) + cards.first() + losingCard
        )
    }

    fun losesRound(): Player {
        return this.copy(cards = cards.subList(1, cards.size))
    }

    fun result(): CombatResult {
        return CombatResult(
            this.name,
            this.cards
        )
    }

    fun hasWon(): Boolean {
        return cards.isEmpty()
    }

}
