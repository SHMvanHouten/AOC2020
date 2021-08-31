package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.util.blocks

fun findCornerTiles(tiles: String): CornerTileArrangement {

    return rearrangeCornerTiles(parseTiles(tiles))
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
