package com.github.shmvanhouten.adventofcode2020.day11

import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.RelativePosition

data class SeatingArea(val emptySeats: Set<Coordinate>, val occupiedSeats: Set<Coordinate> = emptySet(), val previous: SeatingArea? = null ) {

    fun isNotInAStableState() = this.emptySeats != this.previous?.emptySeats && this.occupiedSeats != this.previous?.occupiedSeats

    fun tick(): SeatingArea {
        val (newlyOccupied, stillEmpty) = emptySeats.groupBy { hasAdjacentOccupiedSeats(it, occupiedSeats) }
            .let { (it[false]?.toSet() ?: emptySet()) to (it[true]?.toSet() ?: emptySet()) }
        val (stillOccupied, newlyEmptied) = occupiedSeats.groupBy { hasFourOrMoreAdjacentOccupiedSeats(it, occupiedSeats) }
            .let { (it[false]?.toSet() ?: emptySet()) to (it[true]?.toSet() ?: emptySet()) }

        return SeatingArea(
            stillEmpty + newlyEmptied,
            newlyOccupied + stillOccupied,
            this
        )
    }

    private fun hasFourOrMoreAdjacentOccupiedSeats(seat: Coordinate, occupiedSeats: Set<Coordinate>): Boolean {
        return seat.getSurrounding().count { occupiedSeats.contains(it) } >= 4
    }

    private fun hasAdjacentOccupiedSeats(seat: Coordinate, occupiedSeats: Set<Coordinate>): Boolean {
        return seat.getSurrounding().any { occupiedSeats.contains(it) }
    }

    fun tickCorrectly(): SeatingArea {
        val (newlyOccupied, stillEmpty) = emptySeats
            .groupBy { hasOccupiedSeatsInSight(it) }
            .let { (it[false]?.toSet() ?: emptySet()) to (it[true]?.toSet() ?: emptySet()) }
        val (stillOccupied, newlyEmptied) = occupiedSeats
            .groupBy { hasFiveOrMoreOccupiedSeatsInSight(it) }
            .let { (it[false]?.toSet() ?: emptySet()) to (it[true]?.toSet() ?: emptySet()) }

        return SeatingArea(
            stillEmpty + newlyEmptied,
            newlyOccupied + stillOccupied,
            this
        )
    }

    private val allSeats = emptySeats + occupiedSeats

    private val maxX = allSeats.map { it.x }.max() ?: error("no max X")

    private val maxY = allSeats.map { it.y }.max() ?: error("no max Y")

    private fun hasOccupiedSeatsInSight(seat: Coordinate): Boolean {

        return RelativePosition.values()
            .asSequence()
            .mapNotNull { findFirstSeatInDirection(it, seat) }
            .any { occupiedSeats.contains(it) }
    }

    private fun hasFiveOrMoreOccupiedSeatsInSight(seat: Coordinate): Boolean {
        return RelativePosition.values()
            .mapNotNull { findFirstSeatInDirection(it, seat) }
            .count { occupiedSeats.contains(it) } >= 5
    }

    private fun findFirstSeatInDirection(direction: RelativePosition, seat: Coordinate): Coordinate? {
        return generateSequence(seat.inDirection(direction)) { it.inDirection(direction) }
            .takeWhile { it.isInBounds(0, maxX, 0, maxY) }
            .find { allSeats.contains(it) }

    }
}

fun tickUntilStable(startingSeatingArea: SeatingArea): SeatingArea {
    var seatingArea = startingSeatingArea
    while (seatingArea.isNotInAStableState()) {
        seatingArea = seatingArea.tick()
    }
    return seatingArea
}

fun tickUntilStable2(startingSeatingArea: SeatingArea): SeatingArea {
    var seatingArea = startingSeatingArea
    while (seatingArea.isNotInAStableState()) {
        seatingArea = seatingArea.tickCorrectly()
    }
    return seatingArea
}

