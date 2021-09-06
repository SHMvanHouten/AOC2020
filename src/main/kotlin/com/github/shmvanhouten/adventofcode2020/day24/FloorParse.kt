package com.github.shmvanhouten.adventofcode2020.day24

fun String.parse(): List<PathInstruction> {
    return this.lines()
        .map { toPathInstruction(it) }
}

fun toPathInstruction(line: String): List<Direction> {
    var i = 0
    val instructions = mutableListOf<Direction>()
    while (i < line.length) {
        when (val instruction = line[i].toString()) {
            "e", "w" -> {
                instructions.add(toDirection(instruction))
                i += 1
            }
            "n", "s" -> {
                instructions.add(toDirection(instruction + line[i + 1]))
                i += 2
            }
            else -> error("unknown instruction $instruction")
        }
    }
    return instructions.toList()
}

fun toDirection(string: String): Direction {
    return when(string) {
        "ne" -> Direction.NORTH_EAST
        "nw" -> Direction.NORTH_WEST
        "se" -> Direction.SOUTH_EAST
        "sw" -> Direction.SOUTH_WEST
        "w" -> Direction.WEST
        "e" -> Direction.EAST
        else -> error("Unknown direction $string")
    }
}