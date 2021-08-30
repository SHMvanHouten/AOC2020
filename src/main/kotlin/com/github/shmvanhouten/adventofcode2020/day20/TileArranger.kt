package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.util.blocks

fun findCornerTiles(tiles: String): CornerTileArrangement {

    return rearrangeCornerTiles(parseTiles(tiles))
}

private fun rearrangeCornerTiles(tiles: List<Tile>) = CornerTileArrangement(
    topLeft = tiles.find { it.value.lines().any { line -> line == "..#"  } }!!,
    topRight = tiles.find { it.value.lines().any { line -> line == "#.."  } }!!,
    Tile(-1L, ""),
    Tile(-1L, "")
)

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
