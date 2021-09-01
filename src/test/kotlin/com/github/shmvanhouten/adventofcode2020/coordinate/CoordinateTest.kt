package com.github.shmvanhouten.adventofcode2020.coordinate

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class CoordinateTest {
    @Nested
    inner class To_coordinate_map {
        @Test
        internal fun `finds the coordinates for the char provided`() {
            val coordinates = """
                #.#
                ...
                .#.
            """.trimIndent().toCoordinateMap('#')
            assertThat(
                coordinates,
                equalTo(setOf(Coordinate(0,0), Coordinate(2,0), Coordinate(1,2)))
            )
        }
    }
}