package com.github.shmvanhouten.adventofcode2020.coordinate

import com.github.shmvanhouten.adventofcode2020.coordinate.ClockDirection.CLOCKWISE
import com.github.shmvanhouten.adventofcode2020.coordinate.ClockDirection.COUNTER_CLOCKWISE
import com.github.shmvanhouten.adventofcode2020.coordinate.Degree.*
import com.github.shmvanhouten.adventofcode2020.coordinate.RelativePosition.*
import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    fun getSurrounding(): Set<Coordinate> {
        return setOf(
            this + TOP.coordinate,
            this + TOP_RIGHT.coordinate,
            this + RIGHT.coordinate,
            this + BOTTOM_RIGHT.coordinate,
            this + BOTTOM.coordinate,
            this + BOTTOM_LEFT.coordinate,
            this + LEFT.coordinate,
            this + TOP_LEFT.coordinate
        )
    }

    fun getNeighbour(directionPointed: Direction): Coordinate {
        return when (directionPointed) {
            Direction.NORTH -> this + TOP.coordinate
            Direction.EAST -> this + RIGHT.coordinate
            Direction.SOUTH -> this + BOTTOM.coordinate
            Direction.WEST -> this + LEFT.coordinate
        }
    }

    operator fun plus(otherCoordinate: Coordinate): Coordinate {
        val x = this.x + otherCoordinate.x
        val y = this.y + otherCoordinate.y
        return Coordinate(x, y)
    }

    fun move(direction: Direction, distance: Int = 1): Coordinate {
        return when (direction) {
            Direction.NORTH -> this + Coordinate(0, distance.negate())
            Direction.EAST -> this + Coordinate(distance, 0)
            Direction.SOUTH -> this + Coordinate(0, distance)
            Direction.WEST -> this + Coordinate(distance.negate(), 0)
        }
    }

    fun inDirection(direction: RelativePosition): Coordinate {
        return this + direction.coordinate
    }

    fun isInBounds(minX: Int, maxX: Int, minY: Int, maxY: Int): Boolean =
        this.x in minX..maxX
                && this.y in minY..maxY

    fun distanceFrom(other: Coordinate): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }

    fun times(amount: Int): Coordinate {
        return Coordinate(this.x * amount, this.y * amount)
    }

    fun turnRelativeToOrigin(direction: ClockDirection, degrees: Degree): Coordinate {
        return when {
            shouldRotateASemiCircle(degrees) -> {
                mirror(this)

            }
            shouldRotateClockWise(direction, degrees) -> {
                rotateClockwiseRelativeToOrigin()

            }
            shouldRotateCounterClockwise(direction, degrees) -> {
                rotateCounterClockwiseRelativeToOrigin()

            }
            else -> {
                error("unsupported turn")
            }
        }
    }

    fun rotateCounterClockwiseRelativeToOrigin() = mirror(rotateClockwiseRelativeToOrigin())

    fun rotateClockwiseRelativeToOrigin(): Coordinate {
        return when {
            this.x >= 0 && this.y >= 0 -> {
                this.copy(x = y.negate(), y = x)

            }
            this.x < 0 && this.y >= 0 -> {
                this.copy(x = y.negate(), y = x)

            }
            this.x >= 0 && this.y < 0 -> {
                this.copy(x = y.negate(), y = x)

            }
            this.x < 0 && this.y < 0 -> {
                this.copy(x = y.negate(), y = x)

            }
            else -> {
                error("Down is up and up is down, what is going on?")
            }
        }
    }

    private fun mirror(start: Coordinate) =
        Coordinate(start.x.negate(), start.y.negate())

    private fun shouldRotateASemiCircle(degrees: Degree) = degrees == D180

    private fun shouldRotateClockWise(
        direction: ClockDirection,
        degrees: Degree
    ) = direction == CLOCKWISE && degrees == D90
            || direction == COUNTER_CLOCKWISE && degrees == D270

    private fun shouldRotateCounterClockwise(
        direction: ClockDirection,
        degrees: Degree
    ) = direction == COUNTER_CLOCKWISE && degrees == D90
            || direction == CLOCKWISE && degrees == D270
}

fun Int.negate(): Int {
    return this * -1
}
