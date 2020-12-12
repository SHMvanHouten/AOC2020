package com.github.shmvanhouten.adventofcode2020.day12

fun navigate(ship: Ship, instructions: List<String>): Ship {
    return instructions.fold(ship) { ship: Ship, instruction: String ->
        ship.followInstruction(instruction)
    }

}