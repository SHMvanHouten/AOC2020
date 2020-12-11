package com.github.shmvanhouten.adventofcode2020.day11

import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate

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
        return this;
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

