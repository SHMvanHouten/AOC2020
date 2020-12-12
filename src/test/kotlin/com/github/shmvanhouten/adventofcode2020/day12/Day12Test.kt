package com.github.shmvanhouten.adventofcode2020.day12

import com.github.shmvanhouten.adventofcode2020.coordinate.ClockDirection.CLOCKWISE
import com.github.shmvanhouten.adventofcode2020.coordinate.ClockDirection.COUNTER_CLOCKWISE
import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.Degree.D180
import com.github.shmvanhouten.adventofcode2020.coordinate.Degree.D90
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction.*
import com.github.shmvanhouten.adventofcode2020.coordinate.Turn.LEFT
import com.github.shmvanhouten.adventofcode2020.coordinate.Turn.RIGHT
import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day12Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `going one step in any direction means ship has moved 1 manhattan distance`() {
            var ship = Ship()
            ship = ship.move(NORTH, 1)
            assertThat(ship.distanceFromStart(), equalTo(1) )
        }

        @Test
        internal fun `going 1 step of size 2 in any direction means ship has moved 2 manhattan distance`() {
            var ship = Ship()
            ship = ship.move(NORTH, 2)
            assertThat(ship.distanceFromStart(), equalTo(2) )
        }

        @Test
        internal fun `going 2 steps of size 1 in any (same) direction means ship has moved 2 manhattan distance`() {
            var ship = Ship()
            ship = ship.move(NORTH, 1)
            ship = ship.move(NORTH, 1)
            assertThat(ship.distanceFromStart(), equalTo(2) )
        }

        @Test
        internal fun `going 2 steps north then 1 step south means ship is 1 manhattan distance from start`() {
            var ship = Ship()
            ship = ship.move(NORTH, 2)
            ship = ship.move(SOUTH, 1)
            assertThat(ship.distanceFromStart(), equalTo(1) )
        }

        @Nested
        inner class `Given ship starts facing east` {

            @Test
            internal fun `if we move forward 2, then move west 1, ship will have moved 1`() {
                var ship = Ship()
                ship = ship.moveForward(2)
                ship = ship.move(WEST, 1)
                assertThat(ship.distanceFromStart(), equalTo(1) )
            }

            @Test
            internal fun `if we turn right 90 (now facing south), move forward 2, then north 1, ship has moved 1 manhattan distance`() {
                var ship = Ship()
                ship = ship.turn(RIGHT)
                ship = ship.moveForward(2)
                ship = ship.move(NORTH, 1)
                assertThat(ship.distanceFromStart(), equalTo(1) )
            }

            @Test
            internal fun `if ship turns left 90 (now facing north), and we move 2 forward, then 1 north, ship is 3 manhattan distance removed`() {
                var ship = Ship()
                ship = ship.turn(LEFT)
                ship = ship.moveForward(2)
                ship = ship.move(NORTH, 1)
                assertThat(ship.distanceFromStart(), equalTo(3) )
            }

            @Test
            internal fun `if ship turns left 180 (now facing west), then moves 2 forward, then moves 1 east, ship is 1 manhattan distance away`() {
                var ship = Ship()
                ship = ship.turn(LEFT, D180) // ship faces west
                ship = ship.moveForward(2)
                ship = ship.move(EAST, 1)
                assertThat(ship.distanceFromStart(), equalTo(1) )
            }
        }

        @Test
        internal fun `test input`() {
            val input = """
                |F10
                |N3
                |F7
                |R90
                |F11
            """.trimMargin()
            val instructions = input.lines()
            assertThat(navigate(Ship(), instructions).distanceFromStart(), equalTo(25))
        }

        @Test
        internal fun `part 1`() {
            val input = readFile("/input-day12.txt")
            val instructions = input.lines()
            assertThat(navigate(Ship(), instructions).distanceFromStart(), equalTo(1645))
        }
    }

    @Nested
    inner class Part2 {

        @ParameterizedTest
        @CsvSource(
            "1, 3, -3, 1",
            "1, -3, 3, 1",
            "-1, 3, -3, -1",
            "-1, -3, 3, -1",
            "-3, 1, -1, -3",
            "10, -4, 4, 10"
        )
        internal fun `moving clockwise 90 degrees`(
            startX: Int,
            startY: Int,
            expectedX: Int,
            expectedY: Int
        ) {
            // starting with waypoint relative position of (startX, startY) if we rotate the waypoint right by 90 degrees it will have moved to relative position (expectedX, expectedY)
            var ship = WaypointedShip(waypointRelativePosition = Coordinate(startX, startY))
            ship = ship.rotateWaypoint(CLOCKWISE, D90)
            assertThat(ship.waypointRelativePosition, equalTo(Coordinate(expectedX, expectedY)))
        }

        @ParameterizedTest
        @CsvSource(
            "1, 3, 3, -1",
            "1, -3, -3, -1",
            "-1, 3, 3, 1",
            "-1, -3, -3, 1"
        )
        internal fun `moving counter clockwise 90 degrees`(
            startX: Int,
            startY: Int,
            expectedX: Int,
            expectedY: Int
        ) {
            var ship = WaypointedShip(waypointRelativePosition = Coordinate(startX, startY))
            ship = ship.rotateWaypoint(COUNTER_CLOCKWISE, D90)
            assertThat(ship.waypointRelativePosition, equalTo(Coordinate(expectedX, expectedY)))
        }

        @ParameterizedTest
        @CsvSource(
            "1, 3, -1, -3",
            "1, -3, -1, 3",
            "-1, 3, 1, -3",
            "-1, -3, 1, 3"
        )
        internal fun `moving by 180 degrees in any direction just mirrors the relative position`(
            startX: Int,
            startY: Int,
            expectedX: Int,
            expectedY: Int
        ) {
            var ship = WaypointedShip(waypointRelativePosition = Coordinate(startX, startY))
            ship = ship.rotateWaypoint(COUNTER_CLOCKWISE, D180)
            assertThat(ship.waypointRelativePosition, equalTo(Coordinate(expectedX, expectedY)))
        }

        @Test
        internal fun `test input`() {
            val input = """
                |F10
                |N3
                |F7
                |R90
                |F11
            """.trimMargin()
            val instructions = input.lines()

            assertThat(navigate(WaypointedShip(), instructions).distanceFromStart(), equalTo(286))
        }

        @Test
        internal fun `back where we started`() {
            val input = """
                |F10
                |L180
                |F10
            """.trimMargin()
            val instructions = input.lines()

            assertThat(navigate(WaypointedShip(), instructions).distanceFromStart(), equalTo(0))
        }

        @Test
        internal fun `back where we started 2`() {
            val input = """
                |F10
                |L90
                |L90
                |F10
            """.trimMargin()
            val instructions = input.lines()

            assertThat(navigate(WaypointedShip(), instructions).distanceFromStart(), equalTo(0))
        }

        @Test
        internal fun `part 2`() {
            val input = readFile("/input-day12.txt")
            val instructions = input.lines()

            assertThat(navigate(WaypointedShip(), instructions).distanceFromStart(), equalTo(35292))
        }
    }

}
