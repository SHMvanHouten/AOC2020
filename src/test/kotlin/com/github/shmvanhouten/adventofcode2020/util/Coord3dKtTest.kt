package com.github.shmvanhouten.adventofcode2020.util

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Coord3dKtTest {
    @Test
    internal fun `get neigbours gets the neighbours!`() {

        val neighbours = listOf(
            Coord3d(-1, -1, -1),
            Coord3d(-1, -1, 0),
            Coord3d(-1, -1, 1),
            Coord3d(-1, 0, -1),
            Coord3d(-1, 0, 0),
            Coord3d(-1, 0, 1),
            Coord3d(-1, 1, -1),
            Coord3d(-1, 1, 0),
            Coord3d(-1, 1, 1),
            Coord3d(0, -1, -1),
            Coord3d(0, -1, 0),
            Coord3d(0, -1, 1),
            Coord3d(0, 0, -1),
            Coord3d(0, 0, 1),
            Coord3d(0, 1, -1),
            Coord3d(0, 1, 0),
            Coord3d(0, 1, 1),
            Coord3d(1, -1, -1),
            Coord3d(1, -1, 0),
            Coord3d(1, -1, 1),
            Coord3d(1, 0, -1),
            Coord3d(1, 0, 0),
            Coord3d(1, 0, 1),
            Coord3d(1, 1, -1),
            Coord3d(1, 1, 0),
            Coord3d(1, 1, 1),
        )

        Coord3d(0,0,0).neighbours.forEach {
            assertThat(neighbours, hasElement(it))
        }
        assertThat(Coord3d(0,0,0).neighbours.size, equalTo(neighbours.size))
    }
}