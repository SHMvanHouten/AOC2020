package com.github.shmvanhouten.adventofcode2020.day24

import com.github.shmvanhouten.adventofcode2020.day24.Direction.*

data class HexCoordinate(val x: Int, val y: Int) {

    fun getNeighbouringHexTo(direction: Direction): HexCoordinate {
        return if (this.y.isEven()) {
            when (direction) {
                EAST -> this.copy(x = this.x + 1)
                WEST -> this.copy(x = this.x - 1)
                NORTH_EAST -> HexCoordinate(this.x + 1, this.y - 1)
                SOUTH_EAST -> HexCoordinate(this.x + 1, this.y + 1)
                SOUTH_WEST -> HexCoordinate(this.x, this.y + 1)
                NORTH_WEST -> HexCoordinate(this.x, this.y - 1)
            }
        } else {
            when (direction) {
                EAST -> this.copy(x = this.x + 1)
                WEST -> this.copy(x = this.x - 1)
                NORTH_EAST -> HexCoordinate(this.x, this.y - 1)
                SOUTH_EAST -> HexCoordinate(this.x, this.y + 1)
                SOUTH_WEST -> HexCoordinate(this.x - 1, this.y + 1)
                NORTH_WEST -> HexCoordinate(this.x - 1, this.y - 1)
            }
        }
    }
}

private fun Int.isEven(): Boolean {
    return this % 2 == 0
}

enum class Direction {
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    WEST,
    NORTH_WEST
}