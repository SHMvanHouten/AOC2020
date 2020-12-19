package com.github.shmvanhouten.adventofcode2020.day18

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

fun evaluateWrong(expression: String): Long {
    if(expression.isANumber()) {
        return expression.toLong()
    }

    val (number, rest) = expression.splitIntoTwo(" ")
    return number.toLong().operate(rest[0], evaluateWrong(rest.substring(2)))

}

private fun Long.operate(operator: Char, number: Long): Long {
    return when (operator) {
        '+' -> this + number
        '*' -> this * number
        else -> error("operator is $operator")
    }
}

private fun String.isANumber(): Boolean {
    return this.all { it.isDigit() }
}
