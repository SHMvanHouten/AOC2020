package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.Direction.*
import com.github.shmvanhouten.adventofcode2020.coordinate.bottom
import com.github.shmvanhouten.adventofcode2020.coordinate.top
import com.github.shmvanhouten.adventofcode2020.util.flipped

private val STARTING_COORDINATE = Coordinate(0, 0)

data class TileArrangement(
    val tiles: Map<Coordinate, Tile>,
    val unplacedTiles: Collection<Tile>,
    val lastPlaced: LocatedTile
) {
    fun withBorderlessTiles(): TileArrangement {
        return this.copy(tiles = tiles.mapValues {
            it.value.withoutBorders()
        })
    }
    override fun toString(): String {
        return (0..tiles.map { it.key.y }.max()!!)
            .map { y ->
                concatValues((0..tiles.map { it.key.x }.max()!!)
                    .map { x ->
                        tiles[Coordinate(x, y)]
                    }.map { it?.value?:"..........\n..........\n..........\n..........\n..........\n..........\n..........\n..........\n..........\n..........\n" }
                )
            }.joinToString("\n\n")
    }

    private fun concatValues(tiles: List<String>): String {
        return (0..tiles[0].lines().lastIndex).map { i ->
            tiles.joinToString(" ") { it.lines()[i] }
        }.joinToString("\n")
    }

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
        val tileAbove = tiles[location.getNeighbour(NORTH)]
        return unplacedTiles
            .flatMap { it.inAllRotations() }
            .flatMap { listOf(it, it.flipped) }
            .filter { it.leftSide == lastPlaced.tile.rightSide }
            .filter { tileAbove?.bottomSide?.equals(it.topSide)?:true }
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
            .flatMap { it.inAllRotations() }
            .flatMap { listOf(it, it.flipped) }
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

data class Tile(val id: Long, val value: String) {
    private val lines = value.lines()
    val topSide = lines.first()
    val bottomSide = lines.last()
    val leftSide = lines.map { it.first() }.joinToString("")
    val rightSide = lines.map { it.last() }.joinToString("")
    val flipped: Tile
        get() {
            return this.copy(
                value = value.flipped()
            )
        }
    private val rotatedRight: Tile
        get() {
            val value = rotatedRight(value)
            return this.copy(value = value)
        }

    private fun rotatedRight(value: String): String {
        return (value.lines().first().lastIndex.downTo(0))
            .joinToString("\n") { charPosition ->
                (0..value.lines().lastIndex)
                    .map { lineIndex -> value.lines()[lineIndex] }
                    .map { line -> line[charPosition] }
                    .joinToString("")
            }
    }

    private val rotatedTwice: Tile
        get() {
            return rotatedRight.rotatedRight
        }

    private val rotatedLeft: Tile
        get() {
            return rotatedTwice.rotatedRight
        }

    fun at(location: Coordinate): LocatedTile {
        return LocatedTile(location, this)
    }

    fun inAllRotations(): List<Tile> {
        return listOf(
            this,
            this.rotatedRight,
            this.rotatedTwice,
            this.rotatedLeft
        )
    }

    fun withoutBorders(): Tile {
        return this.copy(value = value.lines()
            .subList(1, this.value.lines().size - 1)
            .map { it.substring(1, it.length - 1) }
            .joinToString("\n")
        )
    }

    override fun equals(other: Any?): Boolean {
        return other is Tile && other.id == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
