package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.util.blocks
import java.util.*
import kotlin.math.sqrt

fun findCornerTiles(tiles: String): TileArrangement {
    return rearrangeTiles(parseTiles(tiles))
}

fun rearrangeTiles(tiles: List<Tile>): TileArrangement {
    val squareSize = sqrt(tiles.size.toFloat()).toInt()
    val unfinishedTileArrangements: Queue<TileArrangement> = LinkedList(
        setupAllTilesInAllRotationsAtStartingPosition(tiles)
    )
    while (unfinishedTileArrangements.isNotEmpty()) {
        val arrangement = unfinishedTileArrangements.poll()!!
        if(arrangement.unplacedTiles.isEmpty()) {
            return arrangement
        }

        if (arrangement.lastPlaced.coordinate.x < squareSize - 1) {
            unfinishedTileArrangements.offer(
                arrangement.placeAllTilesPossiblesToTheRight()
            )
        } else if (arrangement.lastPlaced.coordinate.y < squareSize - 1) {
            unfinishedTileArrangements.offer(
                arrangement.placeAllTilesPossiblesToTheBottom()
            )
        }

    }
    throw Error("No tile arrangements found for tiles $tiles")
}

private fun setupAllTilesInAllRotationsAtStartingPosition(tiles: List<Tile>): List<TileArrangement> {
    return tiles
        .flatMap { it.inAllRotations() }
        .flatMap { listOf(it, it.flipped) }
        .map { TileArrangement(it, tiles - it) }
}

private fun <E> Queue<E>.offer(elements: List<E>) {
    for (element in elements) {
        this.offer(element)
    }
}

fun parseTiles(tiles: String): List<Tile> {
    return tiles.blocks()
        .map { toTile(it) }
}

fun toTile(rawTile: String): Tile {
    return Tile(
        id = rawTile.substring(5, rawTile.indexOf(":")).toLong(),
        rawTile.substringAfter("\n")
    )
}
