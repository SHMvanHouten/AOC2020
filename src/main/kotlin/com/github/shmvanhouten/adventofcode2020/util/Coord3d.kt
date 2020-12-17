package com.github.shmvanhouten.adventofcode2020.util

data class Coord3d(val x: Int, val y: Int, val z: Int) : Coord {
    override operator fun plus(other: Coord): Coord3d {
        return this.add(other as Coord3d)
    }

    override val neighbours: List<Coord3d>
        get() {
            return getNeighboursBeside()
                .flatMap { it.getNeigboursAboveAndBelow() }
                .flatMap { it.getNeighboursInFrontAndBehind() }
                .filter { it != this }
        }

    private fun add(other: Coord3d): Coord3d {
        return Coord3d(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    private fun getNeighboursInFrontAndBehind(): List<Coord3d> =
        (-1..1).map { this + Coord3d(0, 0, it) }

    private fun getNeigboursAboveAndBelow(): List<Coord3d> =
        (-1..1).map { this + Coord3d(0, it, 0) }

    private fun getNeighboursBeside(): List<Coord3d> =
        (-1..1).map { this + Coord3d(it, 0, 0) }
}