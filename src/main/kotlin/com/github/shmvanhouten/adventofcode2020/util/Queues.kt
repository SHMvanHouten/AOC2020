package com.github.shmvanhouten.adventofcode2020.util

import java.util.*

fun <T> queueOf(vararg elements: T):Queue<T> {
    return LinkedList<T>(elements.toList())
}