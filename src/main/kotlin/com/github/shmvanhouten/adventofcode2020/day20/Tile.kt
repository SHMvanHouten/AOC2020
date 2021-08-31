package com.github.shmvanhouten.adventofcode2020.day20

class CornerTileArrangement(private vararg val tiles : Row<Tile>) {
    val top: Row<Tile>
        get() {
            return tiles.first()
        }

    val bottom = tiles.last()
}

class Row<T>(private vararg val elements: T) {
    val left: T
        get() {
            return elements.first()
        }

    val right: T
        get() {
            return elements.last()
        }
}

data class Tile(val id: Long, val value: String) {
    val topSide = value.lines().first()
    val bottomSide = value.lines().last()
    val leftSide = value.lines().map { it.first() }.joinToString("")
    val rightSide = value.lines().map { it.last() }.joinToString("")
}
