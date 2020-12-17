package com.github.shmvanhouten.adventofcode2020.util

data class Coord4d(val x: Int, val y: Int, val z: Int, val w: Int): Coord {

    override fun plus(other: Coord): Coord4d {
        return this.add(other as Coord4d)
    }

    override val neighbours: List<Coord4d>
        get() {
            return getNeighboursBeside()
                .flatMap { it.getNeigboursAbove() }
                .flatMap { it.getNeighboursInFrontAndBehind() }
                .flatMap { it.getNeigboursFromTheFourthDimension() }
                .filter { it != this }
        }

    private fun add(other: Coord4d): Coord4d {
        return Coord4d(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w)
    }

    private fun getNeighboursInFrontAndBehind(): List<Coord4d> =
        (-1..1).map { this + Coord4d(0, 0, it, 0) }

    private fun getNeigboursAbove(): List<Coord4d> =
        (-1..1).map { this + Coord4d(0, it, 0, 0) }

    private fun getNeighboursBeside() =
        (-1..1).map { this + Coord4d(it, 0, 0, 0) }

    private fun getNeigboursFromTheFourthDimension(): List<Coord4d> =
        (-1..1).map { this + Coord4d(0, 0, 0, it) }
}