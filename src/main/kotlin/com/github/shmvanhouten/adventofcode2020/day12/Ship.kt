package com.github.shmvanhouten.adventofcode2020.day12

import com.github.shmvanhouten.adventofcode2019.util.splitIntoTwo
import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.Degree
import com.github.shmvanhouten.adventofcode2020.coordinate.Degree.D90
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction.*
import com.github.shmvanhouten.adventofcode2020.coordinate.Turn
import com.github.shmvanhouten.adventofcode2020.coordinate.Turn.LEFT
import com.github.shmvanhouten.adventofcode2020.coordinate.Turn.RIGHT

data class Ship(val position: Coordinate = Coordinate(0, 0), val directionShipFaces: Direction = EAST) {

    fun move(direction: Direction, amount: Int): Ship {
        return this.copy(position = position.move(direction, amount))
    }

    fun moveForward(amount: Int): Ship {
        return move(directionShipFaces, amount)
    }

    fun distanceFromStart(): Int {
        return position.distanceFrom(Coordinate(0,0))
    }

    fun turn(turn: Turn, degrees: Degree = D90): Ship {
        return this.copy(directionShipFaces = directionShipFaces.turn(turn, degrees))
    }

    fun followInstruction(instruction: String): Ship {
        val (type, amount) = instruction.splitIntoTwo(1)
        return when(type) {
            "N" -> move(NORTH, amount.toInt())
            "E" -> move(EAST, amount.toInt())
            "S" -> move(SOUTH, amount.toInt())
            "W" -> move(WEST, amount.toInt())
            "F" -> moveForward(amount.toInt())
            "R" -> turn(RIGHT, toDegree(amount.toInt()))
            "L" -> turn(LEFT, toDegree(amount.toInt()))
            else -> error("unrecognised instruction: $instruction")

        }
    }

}

fun toDegree(amount: Int): Degree {
    return Degree.values()
        .find { it.degree == amount }
        ?: error("$amount is not a multiple of 90, or too big")

}