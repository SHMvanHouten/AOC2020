package com.github.shmvanhouten.adventofcode2020.day03

import com.github.shmvanhouten.adventofcode2017.day03spiralmemory.Coordinate


fun countTreesInPath(slope: Slope): Int {
    return countTreesInPath(slope, 3, 1)
}

fun countTreesInPath(
    slope: Slope,
    stepWidth: Int,
    stepHeight: Int
) : Int {
    return countTreesInPath(slope.coordinates, slope.width, slope.height, stepWidth, stepHeight)
}

private fun countTreesInPath(
    treeCoordinates: Set<Coordinate>,
    width: Int,
    height: Int,
    stepWidth: Int,
    stepHeight: Int
): Int {
    return 0.until(height).filter { step ->
        treeCoordinates.any { it == Coordinate(stepWidth * step % width, step * stepHeight) }
    }.count()
}
