package com.github.shmvanhouten.adventofcode2020.util

interface Coord {
    operator fun plus(other: Coord) : Coord

    val neighbours: List<Coord>
}
