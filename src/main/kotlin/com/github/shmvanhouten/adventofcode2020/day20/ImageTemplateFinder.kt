package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.orientFromTopLeftMostCoordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.toCoordinateMap
import com.github.shmvanhouten.adventofcode2020.util.flipped
import com.github.shmvanhouten.adventofcode2020.util.rotatedInAllDirections

fun String.findAllInAllOrientations(template: String): Map<String, List<Coordinate>> {
    return this.rotatedInAllDirections()
        .flatMap { listOf(it, it.flipped()) }
        .map { it to it.findAll(template) }
        .toMap()
}

fun String.findAll(template: String): List<Coordinate> {
    return this.toCoordinateMap().findAll(template)
}

fun Set<Coordinate>.findAll(template: String): List<Coordinate> {
    return this.findAll(template.toCoordinateMap().orientFromTopLeftMostCoordinate())
}


private fun Set<Coordinate>.findAll(template: Set<Coordinate>): List<Coordinate> {
    return this.filter { this.matchesTemplateFromCoordinate(it, template) }
        .flatMap { template.fromStartingCoordinate(it) }
}

private fun Set<Coordinate>.fromStartingCoordinate(startingPoint: Coordinate): Set<Coordinate> {
    return this.map { it + startingPoint }.toSet()
}

private fun Set<Coordinate>.matchesTemplateFromCoordinate(startingCoordinate: Coordinate, template: Set<Coordinate>): Boolean {
    return template.asSequence()
        .map { it + startingCoordinate }
        .all { this.contains(it) }
}
