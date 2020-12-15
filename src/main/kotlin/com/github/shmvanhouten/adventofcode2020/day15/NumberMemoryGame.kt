package com.github.shmvanhouten.adventofcode2020.day15

fun findSpokenNumberAt(numberSpoken: Int, input: List<Int>): Int {
    val previousNumbersSpoken = input.toMutableList()
    input.size.until(numberSpoken).forEach{currentNumberSpoken ->
        val init = previousNumbersSpoken.subList(0, previousNumbersSpoken.lastIndex)
        val last = previousNumbersSpoken.last()
        val lastIndexOfNumberThatWasAlreadySpoken = init.lastIndexOf(last)
        if(lastIndexOfNumberThatWasAlreadySpoken != -1) {
            previousNumbersSpoken.add(previousNumbersSpoken.lastIndex - lastIndexOfNumberThatWasAlreadySpoken)
        } else {
            previousNumbersSpoken.add(0)
        }
    }
    return previousNumbersSpoken.last();
}
