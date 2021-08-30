package com.github.shmvanhouten.adventofcode2020.day20

data class CornerTileArrangement(val topLeft: Tile, val topRight: Tile, val bottomLeft: Tile, val bottomRight: Tile)

data class Tile(val id: Long, val value: String)
