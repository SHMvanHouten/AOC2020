package com.github.shmvanhouten.adventofcode2020.day24

class Floor(flipInstructions: String) {
    val tiles = initiate(flipInstructions)

    private fun initiate(flipInstructions: String): Map<HexCoordinate, Boolean> {
        return initiate(flipInstructions.parse())
    }

    private fun initiate(flipInstructions: List<PathInstruction>): Map<HexCoordinate, Boolean> {
        val tiles = mutableMapOf<HexCoordinate, Boolean>()
        flipInstructions.forEach { flip(tiles, it) }
        return tiles.toMap()
    }

    private fun flip(initiateTiles: MutableMap<HexCoordinate, Boolean>, instruction: PathInstruction) {
        var currentCoordinate = HexCoordinate(0,0)
        instruction.forEach { stepInDirection ->
            currentCoordinate = currentCoordinate.getNeighbouringHexTo(stepInDirection)
        }
        initiateTiles.merge(currentCoordinate, true) { v, _ -> !v}
    }
}

typealias PathInstruction = List<Direction>
