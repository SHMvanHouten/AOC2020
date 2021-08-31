package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction.EAST
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction.SOUTH
import com.github.shmvanhouten.adventofcode2020.coordinate.bottom
import com.github.shmvanhouten.adventofcode2020.coordinate.top

private val STARTING_COORDINATE = Coordinate(0, 0)

data class TileArrangement(
    val tiles: Map<Coordinate, Tile>,
    val unplacedTiles: Collection<Tile>,
    val lastPlaced: LocatedTile
) {
    val top: Map<Coordinate, Tile>
        get() {
            return tiles.top()
        }

    val bottom: Map<Coordinate, Tile>
        get() {
            return tiles.bottom()
        }

    fun placeAllTilesPossiblesToTheRight(): List<TileArrangement> {
        val location = lastPlaced.coordinate.getNeighbour(EAST)
        return unplacedTiles
            .filter { it.leftSide == lastPlaced.tile.rightSide }
            .map {
                copy(
                    tiles = tiles.plus(location to it),
                    unplacedTiles = unplacedTiles - it,
                    lastPlaced = it.at(location)
                )
            }
    }

    fun placeAllTilesPossiblesToTheBottom(): List<TileArrangement> {
        val location = lastPlaced.coordinate.getNeighbour(SOUTH).copy(x = 0)
        val tileToGoBelow = tiles[lastPlaced.coordinate.copy(x = 0)]!!
        return unplacedTiles
            .filter { it.topSide == tileToGoBelow.bottomSide }
            .map {
                copy(
                    tiles = tiles.plus(location to it),
                    unplacedTiles = unplacedTiles - it,
                    lastPlaced = it.at(location)
                )
            }
    }

    constructor(tile: Tile, otherTiles: Collection<Tile>) :
            this(mapOf(STARTING_COORDINATE to tile), otherTiles, tile.at(STARTING_COORDINATE))
}

data class LocatedTile(val coordinate: Coordinate, val tile: Tile)

class CornerTileArrangement(private vararg val tiles: Row<Tile>) {
    val top: Row<Tile>
        get() {
            return tiles.first()
        }

    val bottom: Row<Tile>
        get() {
            return tiles.last()
        }
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
    fun at(location: Coordinate): LocatedTile {
        return LocatedTile(location, this)
    }

    val topSide = value.lines().first()
    val bottomSide = value.lines().last()
    val leftSide = value.lines().map { it.first() }.joinToString("")
    val rightSide = value.lines().map { it.last() }.joinToString("")
}
