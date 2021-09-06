package com.github.shmvanhouten.adventofcode2020.day24

class Floor {
    var tiles = mutableMapOf<HexCoordinate, Boolean>()

    fun initiate(flipInstructions: String) {
        initiate(flipInstructions.parse())
    }

    private fun initiate(flipInstructions: List<PathInstruction>) {
        flipInstructions.forEach { flip(it) }
    }

    private fun flip(instruction: PathInstruction) {
        var currentCoordinate = HexCoordinate(0,0)
        instruction.forEach { stepInDirection ->
            currentCoordinate = currentCoordinate.getNeighbouringHexTo(stepInDirection)
        }
        tiles.merge(currentCoordinate, true) { v, _ -> !v}
    }
}

typealias PathInstruction = List<Direction>
