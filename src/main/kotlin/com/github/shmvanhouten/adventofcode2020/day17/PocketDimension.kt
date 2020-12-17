package com.github.shmvanhouten.adventofcode2020.day17

import com.github.shmvanhouten.adventofcode2020.util.Coord

class PocketDimension(val cubes: Map<Coord, Cube>) {
    fun activeCubes(): List<Cube> {
        return cubes.values.filter { it.isActive }
    }

    fun cycle(): PocketDimension {
        return PocketDimension(
            expandCubeDimensions()
                .map { it.copy(isActive = shouldBeActive(it, cubes)) }
                .map { it.location to it }
                .toMap()
        )
    }

    fun cycle(times: Int): PocketDimension {
        return if (times == 0) this
        else this.cycle().cycle(times - 1)
    }

    private fun expandCubeDimensions(): List<Cube> {
        return cubes.values
            .filter { it.isActive }
            .flatMap { cube ->
                cube.location.neighbours
                    .map { location ->
                        cubes.getOrElse(location) { Cube(location, false) }
                    }
            }
    }

    private fun shouldBeActive(cube: Cube, cubes: Map<Coord, Cube>): Boolean {
        return cube.isActive && cube.has2Or3ActiveNeighbours(cubes)
                || cube.has3ActiveNeighbours(cubes)
    }

}

data class Cube(val location: Coord, val isActive: Boolean) {
    fun has2Or3ActiveNeighbours(others: Map<Coord, Cube>): Boolean {
        return this.location.neighbours.asSequence()
            .filter { others[it]?.isActive ?: false }
            .take(4).toList()
            .size in 2..3
    }

    fun has3ActiveNeighbours(others: Map<Coord, Cube>): Boolean {
        // todo: duplication
        return this.location.neighbours.asSequence()
            .filter { others[it]?.isActive ?: false }
            .take(4).toList()
            .size == 3
    }
}
