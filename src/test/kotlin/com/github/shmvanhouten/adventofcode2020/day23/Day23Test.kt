package com.github.shmvanhouten.adventofcode2020.day23

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day23Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `(3) 8  9  1  2  5  4  6  7 after 1 step becomes 3 (2) 8  9  1  5  4  6  7`() {
            val cupsGame = CupsGame(listOf(3,8,9,1,2,5,4,6,7))

            cupsGame.act()
            assertThat(cupsGame.printCups(), equalTo("289154673"))
        }

        @Test
        internal fun `3 (2) 8  9  1  5  4  6  7 after 1 step becomes 3  2 (5) 4  6  7  8  9  1`() {
            val cupsGame = CupsGame(listOf(2,8,9,1,5,4,6,7,3))

            cupsGame.act()
            assertThat(cupsGame.printCups(), equalTo("546789132"))
        }

        @Test
        internal fun `(5) 4  6  7  8  9  1  3  2 after 1 step becomes 5 (8) 9  1  3  4  6  7  2`() {
            val cupsGame = CupsGame(listOf(5,4,6,7,8,9,1,3,2))

            cupsGame.act()
            assertThat(cupsGame.printCups(), equalTo("891346725"))
        }

        @Test
        internal fun `136792584 after 1 step becomes (9) 3  6  7  2  5  8  4  1`() {
            val cupsGame = CupsGame(listOf(1,3,6,7,9,2,5,8,4))

            cupsGame.act()
            assertThat(cupsGame.printCups(), equalTo("936725841"))
        }

        @Test
        internal fun `after 10 moves (3) 8  9  1  2  5  4  6  7 becomes (8) 3  7  4  1  9  2  6  5`() {
            val cupsGame = CupsGame(listOf(3,8,9,1,2,5,4,6,7))

            val gameAfterMove = act(cupsGame, 10)
            assertThat(gameAfterMove.printCups(), equalTo("837419265"))
        }

        @Test
        internal fun `input 389125467, after 10 moves the order after 1 is 92658374`() {
            val cupsGame = CupsGame(listOf(3,8,9,1,2,5,4,6,7))

            val gameAfterMove = act(cupsGame, 10)
            assertThat(gameAfterMove.printCupsAfter1(), equalTo("92658374"))
        }

        @Test
        internal fun `input 389125467, after 100 moves the order after 1 is 67384529`() {
            val cupsGame = CupsGame(listOf(3,8,9,1,2,5,4,6,7))

            val gameAfterMove = act(cupsGame, 100)
            assertThat(gameAfterMove.printCupsAfter1(), equalTo("67384529"))
        }

        @Test
        internal fun `part 1`() {
            val cupsGame = CupsGame(listOf(9,6,2,7,1,3,8,5,4))
            val gameAfterMove = act(cupsGame, 100)
            assertThat(gameAfterMove.printCupsAfter1(), equalTo("65432978"))
        }
    }

    @Nested
    inner class Part_2
    {
        @Test
        internal fun `part 2`() {
            val cupsLabeling = listOf(9,6,2,7,1,3,8,5,4) + (10..1_000_000)
            val cupsGame = CupsGame(cupsLabeling)
            val gameAfterMove = act(cupsGame, 10_000_000)
            val (cup1, cup2) = gameAfterMove.get2CupsAfter1()

            assertThat(cup1.toLong() * cup2, equalTo(287230227046))
        }
    }

}
