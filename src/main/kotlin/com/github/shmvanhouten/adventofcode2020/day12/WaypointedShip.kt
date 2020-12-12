package com.github.shmvanhouten.adventofcode2020.day12

import com.github.shmvanhouten.adventofcode2019.util.splitIntoTwo
import com.github.shmvanhouten.adventofcode2020.coordinate.ClockDirection
import com.github.shmvanhouten.adventofcode2020.coordinate.ClockDirection.CLOCKWISE
import com.github.shmvanhouten.adventofcode2020.coordinate.ClockDirection.COUNTER_CLOCKWISE
import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.Degree
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction.*

data class WaypointedShip(
    val shipPosition: Coordinate = Coordinate(0, 0),
    val waypointRelativePosition: Coordinate = Coordinate(10, -1)
) {

    fun followInstruction(instruction: String): WaypointedShip {
        val (type, amount) = instruction.splitIntoTwo(1)
        return when(type) {
            "N" -> moveWaypoint(NORTH, amount.toInt())
            "E" -> moveWaypoint(EAST, amount.toInt())
            "S" -> moveWaypoint(SOUTH, amount.toInt())
            "W" -> moveWaypoint(WEST, amount.toInt())
            "F" -> moveForward(amount.toInt())
            "R" -> rotateWaypoint(CLOCKWISE, toDegree(amount.toInt()))
            "L" -> rotateWaypoint(COUNTER_CLOCKWISE, toDegree(amount.toInt()))
            else -> error("unsupported instruction: $instruction")

        }
    }

    fun distanceFromStart(): Int {
        return shipPosition.distanceFrom(Coordinate(0,0))
    }

    fun moveForward(amount: Int): WaypointedShip {
        return this.copy(
            shipPosition = shipPosition + (waypointRelativePosition.times(amount))
        )
    }

    fun moveWaypoint(direction: Direction, amount: Int): WaypointedShip {
        return this.copy(
            waypointRelativePosition = waypointRelativePosition.move(direction, amount)
        )
    }

    fun rotateWaypoint(direction: ClockDirection, degrees: Degree): WaypointedShip {
        return this.copy(
            waypointRelativePosition = waypointRelativePosition.turnRelativeToOrigin(direction, degrees)
        )
    }

}