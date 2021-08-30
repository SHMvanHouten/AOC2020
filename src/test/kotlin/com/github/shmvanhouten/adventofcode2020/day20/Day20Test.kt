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
                |...
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.topLeft.id, equalTo(secondID))
            assertThat(
                cornerTileArrangement.topLeft.value,
                equalTo(
                    """
                    |..#
                    |#.#
                    |...
                    """.trimMargin()
                )
            )
            assertThat(cornerTileArrangement.topRight.id, equalTo(firstID))
            assertThat(
                cornerTileArrangement.topRight.value,
                equalTo(
                    """
                    |#.#
                    |#..
                    |...
                    """.trimMargin()
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
                |...
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.topLeft.id, equalTo(456))
            assertThat(
                cornerTileArrangement.topLeft.value,
                equalTo(
                    """
                    |#.#
                    |..#
                    |...
                    """.trimMargin()
                )
            )
            assertThat(cornerTileArrangement.topRight.id, equalTo(123))
            assertThat(
                cornerTileArrangement.topRight.value,
                equalTo(
                    """
                    |#..
                    |#.#
                    |...
                    """.trimMargin()
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
                |#.#
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.topLeft.id, equalTo(456))
            assertThat(
                cornerTileArrangement.topLeft.value,
                equalTo(
                    """
                    |...
                    |#.#
                    |..#
                    """.trimMargin()
                )
            )
            assertThat(cornerTileArrangement.topRight.id, equalTo(123))
            assertThat(
                cornerTileArrangement.topRight.value,
                equalTo(
                    """
                    |...
                    |#..
                    |#.#
                    """.trimMargin()
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
                |#..
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.topLeft.id, equalTo(456))
            assertThat(
                cornerTileArrangement.topLeft.value,
                equalTo(
                    """
                    |..#
                    |...
                    |#.#
                    """.trimMargin()
                )
            )
            assertThat(cornerTileArrangement.topRight.id, equalTo(123))
            assertThat(
                cornerTileArrangement.topRight.value,
                equalTo(
                    """
                    |#..
                    |..#
                    |#..
                    """.trimMargin()
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
                |#..
                """.trimMargin()
            val cornerTileArrangement: CornerTileArrangement = findCornerTiles(tiles)
            assertThat(cornerTileArrangement.topLeft.id, equalTo(123))
            assertThat(cornerTileArrangement.topRight.id, equalTo(456))
        }

        // todo: remember they can be turned as well..
    }

}
