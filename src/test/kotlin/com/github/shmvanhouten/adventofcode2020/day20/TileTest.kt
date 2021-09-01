package com.github.shmvanhouten.adventofcode2020.day20

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

internal class TileTest {
    @Test
    internal fun `a tile can be rotated in all directions`() {
        val tile = Tile(
            -1, """
            |..#
            |##.
            |#..
        """.trimMargin()
        )
        assertThat(
            tile.inAllRotations().map { it.value },
            equalTo(
                listOf(
                    """
                        |..#
                        |##.
                        |#..
                    """.trimMargin(),
                    """
                        |#..
                        |.#.
                        |.##
                    """.trimMargin(),
                    """
                        |..#
                        |.##
                        |#..
                    """.trimMargin(),
                    """
                        |##.
                        |.#.
                        |..#
                    """.trimMargin()
                )
            )
        )
    }

    @Test
    internal fun `a tile can be flipped`() {
        val tileValue = """
            ..#.##...#
            #.#..#.#..
            .#....###.
            .##.##.###
            #####.###.
            #....#.##.
            ######...#
            ##..#.....
            #...####.#
            .##...##.#
        """.trimIndent()
        val tile = Tile(-1, tileValue)
        assertThat(
            tile.flipped.value,
            equalTo("""
                #...##.#..
                ..#.#..#.#
                .###....#.
                ###.##.##.
                .###.#####
                .##.#....#
                #...######
                .....#..##
                #.####...#
                #.##...##.
            """.trimIndent())
        )
    }
}