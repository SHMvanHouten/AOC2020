package com.github.shmvanhouten.adventofcode2020.day20

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class Day20Test {

    @Nested
    inner class Part1 {

        @ParameterizedTest
        @CsvSource(
            "123, 456",
            "234, 567"
        )
        internal fun `three by three tiles that line up one possible way`(
            firstID: Long,
            secondID: Long
        ) {
            val tiles = """
                |tile $firstID:
                |#.#
                |#..
                |...
                |
                |tile $secondID:
                |..#
                |#.#
                |...""".trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(secondID))
            assertThat(
                cornerTileArrangement.top.left.value,
                equalTo(
                    """
                    |..#
                    |#.#
                    |...""".trimMargin()
                )
            )
            assertThat(cornerTileArrangement.top.right.id, equalTo(firstID))
            assertThat(
                cornerTileArrangement.top.right.value,
                equalTo(
                    """
                    |#.#
                    |#..
                    |...""".trimMargin()
                )
            )
        }

        @Test
        internal fun `three by three tiles that are already lined up`() {
            val tiles = """
                |tile 456:
                |#.#
                |..#
                |...
                |
                |tile 123:
                |#..
                |#.#
                |...""".trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(456))
            assertThat(
                cornerTileArrangement.top.left.value,
                equalTo(
                    """
                    |#.#
                    |..#
                    |...""".trimMargin()
                )
            )
            assertThat(cornerTileArrangement.top.right.id, equalTo(123))
            assertThat(
                cornerTileArrangement.top.right.value,
                equalTo(
                    """
                    |#..
                    |#.#
                    |...""".trimMargin()
                )
            )
        }

        @Test
        internal fun `three by three tiles with the top row empty`() {
            val tiles = """
                |tile 456:
                |...
                |#.#
                |..#
                |
                |tile 123:
                |...
                |#..
                |#.#""".trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(456))
            assertThat(
                cornerTileArrangement.top.left.value,
                equalTo(
                    """
                    |...
                    |#.#
                    |..#""".trimMargin()
                )
            )
            assertThat(cornerTileArrangement.top.right.id, equalTo(123))
            assertThat(
                cornerTileArrangement.top.right.value,
                equalTo(
                    """
                    |...
                    |#..
                    |#.#""".trimMargin()
                )
            )
        }

        @Test
        internal fun `three by three tiles with the middle row empty`() {
            val tiles = """
                |tile 456:
                |..#
                |...
                |#.#
                |
                |tile 123:
                |#..
                |..#
                |#..""".trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(456))
            assertThat(
                cornerTileArrangement.top.left.value,
                equalTo(
                    """
                    |..#
                    |...
                    |#.#""".trimMargin()
                )
            )
            assertThat(cornerTileArrangement.top.right.id, equalTo(123))
            assertThat(
                cornerTileArrangement.top.right.value,
                equalTo(
                    """
                    |#..
                    |..#
                    |#..""".trimMargin()
                )
            )
        }

        @Test
        internal fun `the tiles' empty sides fit together`() {
            val tiles = """
                |tile 456:
                |..#
                |..#
                |...
                |
                |tile 123:
                |#..
                |...
                |#..""".trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(123))
            assertThat(cornerTileArrangement.top.right.id, equalTo(456))
        }

        @Test
        internal fun `the tiles line up top to bottom as well`() {
            val tiles = """
                |tile 123:
                |##.
                |...
                |.#.
                |
                |tile 456:
                |..#
                |..#
                |...
                |
                |tile 777:
                |.#.
                |...
                |#.#
                |
                |tile 888:
                |...
                |..#
                |#..
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(123))
            assertThat(cornerTileArrangement.top.right.id, equalTo(456))
            assertThat(cornerTileArrangement.bottom.left.id, equalTo(777))
            assertThat(cornerTileArrangement.bottom.right.id, equalTo(888))
        }

        @Test
        internal fun `the tiles line up top to bottom as well with last two tiles switched positions`() {
            val tiles = """
                |tile 123:
                |##.
                |...
                |.#.
                |
                |tile 456:
                |..#
                |..#
                |...
                |
                |tile 888:
                |...
                |..#
                |#..
                |
                |tile 777:
                |.#.
                |...
                |#.#
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(123))
            assertThat(cornerTileArrangement.top.right.id, equalTo(456))
            assertThat(cornerTileArrangement.bottom.left.id, equalTo(777))
            assertThat(cornerTileArrangement.bottom.right.id, equalTo(888))
        }

        @Test
        internal fun `the tiles line up top to bottom as well with first and last tile switched positions`() {
            val tiles = """
                |tile 777:
                |.#.
                |...
                |#.#
                |
                |tile 456:
                |..#
                |..#
                |...
                |
                |tile 888:
                |...
                |..#
                |#..
                |
                |tile 123:
                |##.
                |...
                |.#.
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.top.left.id, equalTo(123))
            assertThat(cornerTileArrangement.top.right.id, equalTo(456))
            assertThat(cornerTileArrangement.bottom.left.id, equalTo(777))
            assertThat(cornerTileArrangement.bottom.right.id, equalTo(888))
        }
        // todo: remember they can be turned as well..
    }

}
