package com.github.shmvanhouten.adventofcode2020.day03

import com.github.shmvanhouten.adventofcode2017.day03spiralmemory.Coordinate
import com.github.shmvanhouten.adventofcode2019.day10.parse

data class Slope(val coordinates: Set<Coordinate>, val width: Int, val height: Int)

fun toSlope(input: String): Slope {
    val split = input.split('\n')
    return Slope(parse(input), split[0].length, split.size)
}

