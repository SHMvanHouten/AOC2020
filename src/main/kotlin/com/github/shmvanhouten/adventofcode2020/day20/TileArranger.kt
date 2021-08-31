package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.util.blocks
import java.util.*
import kotlin.math.sqrt

fun findCornerTiles(tiles: String): TileArrangement {
    return rearrangeTiles(parseTiles(tiles))
//    return rearrangeCornerTiles(parseTiles(tiles))
}

fun rearrangeTiles(tiles: List<Tile>): TileArrangement {
    val squareSize = sqrt(tiles.size.toFloat()).toInt()
    val unfinishedTileArrangements: Queue<TileArrangement> = LinkedList(tiles.map { TileArrangement(it, tiles - it) })
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

private fun <E> Queue<E>.offer(elements: List<E>) {
    for (element in elements) {
        this.offer(element)
    }
}

private fun rearrangeCornerTiles(tiles: List<Tile>): CornerTileArrangement {

    return CornerTileArrangement(
        determineTopRow(tiles),
        determineBottomRow(tiles)
    )
}

fun determineBottomRow(tiles: List<Tile>): Row<Tile> {
    val tile1 = tiles[tiles.size - 2]
    val tile2 = tiles.last()
    return determineOrder(tile1, tile2)
}

private fun determineTopRow(tiles: List<Tile>): Row<Tile> {
    val tile1 = tiles[0]
    val tile2 = tiles[1]
    return determineOrder(tile1, tile2)
}

private fun determineOrder(
    tile1: Tile,
    tile2: Tile
): Row<Tile> {
    return if (tile1.rightSide == tile2.leftSide) {
        Row(tile1, tile2)
    } else {
        Row(tile2, tile1)
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
