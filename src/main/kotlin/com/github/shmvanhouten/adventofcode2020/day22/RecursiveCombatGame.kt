package com.github.shmvanhouten.adventofcode2020.day22

fun play(game: RecursiveCombatGame): CombatResult {
//    println("=== Game ${game.gameDepth} ===")
    var combatGame = game
    while (combatGame.hasNoWinner()) {
        combatGame = combatGame.playRound()
    }
//    println()
    return calculateResult(combatGame)
}

fun calculateResult(combatGame: RecursiveCombatGame): CombatResult {
    return combatGame.winner?.result()?: throw Error("tried to calculate result when no player has won yet")
}

class RecursiveCombatGame (
    val player1: RecursivePlayer,
    val player2: RecursivePlayer,
    private val previousRounds: Set<String> = emptySet(),
    val gameDepth : Int = 1,
    val round : Int = 1
){
    var winner: RecursivePlayer? = ascertainWinnerIfAvailable()

    private fun ascertainWinnerIfAvailable(): RecursivePlayer? {
        return if(previousRounds.contains(this.toString()) || player2.cards.isEmpty()) {
            player1
        } else if(player1.cards.isEmpty()) {
            player2
        } else null
    }

    fun hasNoWinner(): Boolean {
        return winner == null
    }

    fun playRound(): RecursiveCombatGame {
//        println()
//        println("-- Round $round (Game $gameDepth) --")
//        println("Player 1's deck: ${player1.cards}")
//        println("Player 2's deck: ${player2.cards}")
//        println("Player 1 plays: ${player1.nextCard()}")
//        println("Player 2 plays: ${player2.nextCard()}")
        if(player1.wantsToRecurse() && player2.wantsToRecurse()) {
//            println("Playing a sub-game to determine the winner...")
            val recursiveGame = RecursiveCombatGame(
                player1.recursiveHand(),
                player2.recursiveHand(),
                gameDepth = gameDepth + 1
            )
            val (recursiveGameWinner, _) = play(recursiveGame)
            return if(recursiveGameWinner == player1.name) {
                player1WinsRound()
            } else {
                player2WinsRound()
            }
        } else if(player1.nextCard() > player2.nextCard()) {
            return player1WinsRound()
        } else {
            return player2WinsRound()
        }
    }

    private fun player1WinsRound(): RecursiveCombatGame {
//        println("Player 1 wins round $round of game $gameDepth!")
        return RecursiveCombatGame(
            player1.winsRound(player2.nextCard()),
            player2.losesRound(),
            previousRounds + this.toString(),
            gameDepth = gameDepth,
            round = round + 1
        )
    }

    private fun player2WinsRound(): RecursiveCombatGame {
//        println("Player 2 wins round $round of game $gameDepth!")
        return RecursiveCombatGame(
            player1.losesRound(),
            player2.winsRound(player1.nextCard()),
            previousRounds + this.toString(),
            gameDepth = gameDepth,
            round = round + 1
        )
    }

    override fun toString(): String {
        return "${player1.cards} ${player2.cards}"
    }


}

data class RecursivePlayer(val cards: List<Int>, val name: String) {
    fun nextCard(): Int {
        return cards.first()
    }

    fun winsRound(losingCard: Int): RecursivePlayer {
        return this.copy(
            cards = cards.subList(1, cards.size) + cards.first() + losingCard
        )
    }

    fun losesRound(): RecursivePlayer {
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

    fun wantsToRecurse(): Boolean {
        return nextCard() <= cards.size - 1
    }

    fun recursiveHand(): RecursivePlayer {
        return this.copy(cards = cards.subList(1, nextCard() + 1))
    }

}