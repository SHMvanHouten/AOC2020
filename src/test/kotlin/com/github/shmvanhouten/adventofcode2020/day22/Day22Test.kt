package com.github.shmvanhouten.adventofcode2020.day22

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.github.shmvanhouten.adventofcode2020.util.blocks
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day22Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `the higher card wins the round`() {
            val combat = CombatGame(
                Player(listOf(9), "player1"),
                Player(listOf(8), "player2")
            )
            val combatAfterRound = combat.playRound()

            assertThat(combatAfterRound.player1.cards, equalTo(listOf(9,8)))
            assertThat(combatAfterRound.player2.cards, equalTo(emptyList()))
        }

        @Test
        internal fun `after a player wins their score is calculated`() {
            val combat = CombatGame(
                Player(listOf(9), "player1"),
                Player(listOf(8), "player1")
            )
            val result = play(combat)

            assertThat(result.winner, equalTo("player1"))
            assertThat(result.score, equalTo(8 + (9 * 2)))
        }

        @Test
        internal fun example1() {
            val combat = example1.toCombatGame()

            val result = play(combat)

            assertThat(result.winner, equalTo("Player 2"))
            assertThat(result.score, equalTo(306))
        }

        @Test
        internal fun `part 1`() {
            val combat = readFile("/input-day22.txt").toCombatGame()
            val result = play(combat)

            assertThat(result.winner, equalTo("Player 1"))
            assertThat(result.score, equalTo(36257))
        }
    }

    @Nested
    inner class Part_2_recursive_combat {

        @Test
        internal fun example1() {
            val combat = example1.toRecursiveCombatGame()

            val result = play(combat)

            assertThat(result.winner, equalTo("Player 2"))
            assertThat(result.score, equalTo(291))
        }

        @Test
        internal fun `infinite recursive example`() {
            val combat = infiniteRecursiveExample.toRecursiveCombatGame()

            val result = play(combat)

            assertThat(result.winner, equalTo("Player 1"))
        }

        @Test
        internal fun `part 2`() {
            val combat = readFile("/input-day22.txt").toRecursiveCombatGame()
            val result = play(combat)

            assertThat(result.winner, equalTo("Player 1"))
            assertThat(result.score, equalTo(33304))
        }
    }

}

private fun String.toRecursiveCombatGame(): RecursiveCombatGame {
    val players = this.blocks()
    val player1 = players[0].lines()
    val player2 = players[1].lines()
    return RecursiveCombatGame(
        player1.toRecursivePlayer(),
        player2.toRecursivePlayer()
    )
}

private fun String.toCombatGame(): CombatGame {
    val players = this.blocks()
    val player1 = players[0].lines()
    val player2 = players[1].lines()
    return CombatGame(
        player1.toPlayer(),
        player2.toPlayer()
    )
}

private fun List<String>.toPlayer(): Player {
    return Player(
        this.subList(1, this.size).map { it.toInt() },
        this.first().substring(0, this.first().length - 1)
    )
}

private fun List<String>.toRecursivePlayer(): RecursivePlayer {
    return RecursivePlayer(
        this.subList(1, this.size).map { it.toInt() },
        this.first().substring(0, this.first().length - 1)
    )
}

val example1 = """
    Player 1:
    9
    2
    6
    3
    1

    Player 2:
    5
    8
    4
    7
    10
""".trimIndent()

val infiniteRecursiveExample = """
    Player 1:
    43
    19

    Player 2:
    2
    29
    14
""".trimIndent()