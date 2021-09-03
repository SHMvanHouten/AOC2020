package com.github.shmvanhouten.adventofcode2020.day22

fun play(game: CombatGame): CombatResult {
    var combatGame = game
    while (combatGame.hasNoWinner()) {
        combatGame = combatGame.playRound()
    }
    return calculateResult(combatGame)
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

    fun hasNoWinner(): Boolean {
        return player1.cards.isNotEmpty() && player2.cards.isNotEmpty()
    }

    private fun player1WinsRound(): CombatGame {
        return CombatGame(
            player1.wins(player2.nextCard()),
            player2.loses()
        )
    }

    private fun player2WinsRound(): CombatGame {
        return CombatGame(
            player2.wins(player1.nextCard()),
            player1.loses()
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

data class CombatResult(val winner: String, val score: Long)

data class Player(val cards: List<Int>, val name: String) {
    fun nextCard(): Int {
        return cards.first()
    }

    fun wins(losingCard: Int): Player {
        return this.copy(
            cards = cards.subList(1, cards.size) + cards.first() + losingCard
        )
    }

    fun loses(): Player {
        return this.copy(cards = cards.subList(1, cards.size))
    }

    fun result(): CombatResult {
        return CombatResult(
            this.name,
            this.cards.reversed().mapIndexed { i, card ->
                (i + 1L) * card
            }.sum()
        )
    }

}
