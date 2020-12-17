package com.github.shmvanhouten.adventofcode2020.util

import com.github.shmvanhouten.adventofcode2019.day12.Coord3d

fun Coord3d.neighbours(): List<Coord3d> {
    return getNeighboursBeside()
        .flatMap { it.getNeigboursAbove() }
        .flatMap { it.getNeighboursInFrontAndBehind() }
        .filter { it != this }
}

private fun Coord3d.getNeighboursInFrontAndBehind(): List<Coord3d> =
    (-1..1).map { this + Coord3d(0, 0, it) }

private fun Coord3d.getNeigboursAbove(): List<Coord3d> =
    (-1..1).map { this + Coord3d(0, it, 0) }

private fun Coord3d.getNeighboursBeside() =
    (-1..1).map { this + Coord3d(it, 0, 0) }