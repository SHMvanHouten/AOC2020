package com.github.shmvanhouten.adventofcode2020.util

fun String.blocks(): List<String> {
    return this.split("\n\n")
}