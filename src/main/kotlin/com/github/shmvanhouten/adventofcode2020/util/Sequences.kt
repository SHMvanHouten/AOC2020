package com.github.shmvanhouten.adventofcode2020.util

fun <T> Sequence<T>.repeat(): Sequence<T> =
    generateSequence(this) { this }.flatten()

fun IntProgression.repeat(): Sequence<Int> =
    this.asSequence().repeat()