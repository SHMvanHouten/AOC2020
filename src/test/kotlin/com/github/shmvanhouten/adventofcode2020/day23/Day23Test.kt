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
            val cupsGame = CupsGame("389125467")

            val gameAfterMove = cupsGame.act()
            assertThat(gameAfterMove.printCups(), equalTo("289154673"))
        }

        @Test
        internal fun `3 (2) 8  9  1  5  4  6  7 after 1 step becomes 3  2 (5) 4  6  7  8  9  1`() {
            val cupsGame = CupsGame("289154673")

            val gameAfterMove = cupsGame.act()
            assertThat(gameAfterMove.printCups(), equalTo("546789132"))
        }

        @Test
        internal fun `(5) 4  6  7  8  9  1  3  2 after 1 step becomes 5 (8) 9  1  3  4  6  7  2`() {
            val cupsGame = CupsGame("546789132")

            val gameAfterMove = cupsGame.act()
            assertThat(gameAfterMove.printCups(), equalTo("891346725"))
        }

        @Test
        internal fun `136792584 after 1 step becomes (9) 3  6  7  2  5  8  4  1`() {
            val cupsGame = CupsGame("136792584")

            val gameAfterMove = cupsGame.act()
            assertThat(gameAfterMove.printCups(), equalTo("936725841"))
        }

        @Test
        internal fun `after 10 moves (3) 8  9  1  2  5  4  6  7 becomes (8) 3  7  4  1  9  2  6  5`() {
            val cupsGame = CupsGame("389125467")

            val gameAfterMove = act(cupsGame, 10)
            assertThat(gameAfterMove.printCups(), equalTo("837419265"))
        }

        @Test
        internal fun `input 389125467, after 10 moves the order after 1 is 92658374`() {
            val cupsGame = CupsGame("389125467")

            val gameAfterMove = act(cupsGame, 10)
            assertThat(gameAfterMove.printCupsAfter1(), equalTo("92658374"))
        }

        @Test
        internal fun `input 389125467, after 100 moves the order after 1 is 67384529`() {
            val cupsGame = CupsGame("389125467")

            val gameAfterMove = act(cupsGame, 100)
            assertThat(gameAfterMove.printCupsAfter1(), equalTo("67384529"))
        }

        @Test
        internal fun `part 1`() {
            val cupsGame = CupsGame("962713854")
            val gameAfterMove = act(cupsGame, 100)
            assertThat(gameAfterMove.printCupsAfter1(), equalTo("65432978"))
        }
    }

}
