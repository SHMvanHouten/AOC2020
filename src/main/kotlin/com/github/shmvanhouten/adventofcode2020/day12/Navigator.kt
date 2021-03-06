package com.github.shmvanhouten.adventofcode2020.day12

fun navigate(startingShip: Ship, instructions: List<String>): Ship {
    return instructions.fold(startingShip) { ship, instruction ->
        ship.followInstruction(instruction)
    }

}

fun navigate(startingShip: WaypointedShip, instructions: List<String>): WaypointedShip {
    return instructions.fold(startingShip) { ship, instruction ->
        ship.followInstruction(instruction)
    }
}