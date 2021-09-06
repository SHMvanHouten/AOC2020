package com.github.shmvanhouten.adventofcode2020.day24

class Floor(val blackTiles: Set<HexCoordinate>) {

    constructor(flipInstructions: String) : this(initiate(flipInstructions))

    fun countBlackTiles(): Int {
        return blackTiles.size
    }

    fun tick(): Floor {
        val blackTilesAfterFlip = blackTiles - listBlackTilesToFlip()
        return Floor(blackTilesAfterFlip + listWhiteTilesToFlip())
    }

    private fun listWhiteTilesToFlip(): Set<HexCoordinate> {
        return blackTiles
            .flatMap { it.listSurrounding() }
            .filter { isWhiteTile(it) }
            .filter { countSurroundingBlackCoordinates(it) == 2 }
            .toSet()
    }

    private fun listBlackTilesToFlip() = blackTiles
        .filter { coord ->
            val surroundingBlackCoordinates = countSurroundingBlackCoordinates(coord)
            surroundingBlackCoordinates == 0 || surroundingBlackCoordinates > 2
        }

    private fun countSurroundingBlackCoordinates(coord: HexCoordinate): Int {
        return coord.listSurrounding()
            .count { isBlackTile(it) }
    }

    private fun isWhiteTile(it: HexCoordinate) = !isBlackTile(it)

    private fun isBlackTile(coordinate: HexCoordinate): Boolean {
        return blackTiles.contains(coordinate)
    }

}

fun tick(floor: Floor, days: Int): Floor {
    var newFloor = floor
    repeat(days) {
        newFloor = newFloor.tick()
    }
    return newFloor
}

private fun initiate(flipInstructions: String): Set<HexCoordinate> {
    return initiate(flipInstructions.parse())
}

private fun initiate(flipInstructions: List<PathInstruction>): Set<HexCoordinate> {
    val tiles = mutableMapOf<HexCoordinate, Boolean>()
    flipInstructions.forEach { flip(tiles, it) }
    println("${tiles.count { !it.value }} double flipped")
    return tiles.filter { it.value }.keys
}

private fun flip(initiateTiles: MutableMap<HexCoordinate, Boolean>, instruction: PathInstruction) {
    var currentCoordinate = HexCoordinate(0,0)
    instruction.forEach { stepInDirection ->
        currentCoordinate = currentCoordinate.getNeighbouringHexTo(stepInDirection)
    }
    flipTileAt(initiateTiles, currentCoordinate)
}

private fun flipTileAt(
    tiles: MutableMap<HexCoordinate, Boolean>,
    coordinate: HexCoordinate
) {
    tiles.merge(coordinate, true) { v, _ -> !v }
}

typealias PathInstruction = List<Direction>
