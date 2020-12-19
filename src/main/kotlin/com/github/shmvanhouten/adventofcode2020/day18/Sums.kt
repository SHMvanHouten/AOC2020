package com.github.shmvanhouten.adventofcode2020.day18

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

private fun oldEval(sum: String): Long {

    var resultSoFar = sum.substring(0, sum.indexOf(" ")).toLong()
    var nextOperator = findFirstOperator(sum)
    var remaining = getTheRest(nextOperator!!, sum)
    while (nextOperator != null) {
        val (result, rest) = when (nextOperator) {
            Operator.PLUS -> sum(resultSoFar, remaining)
            Operator.TIMES -> multiply(resultSoFar, remaining)
        }
        remaining = rest
        resultSoFar = result
        nextOperator = findFirstOperator(remaining)
    }

    return resultSoFar
}

private fun getTheRest(operator: Operator, sum: String): String {
    return when (operator) {
        Operator.PLUS -> sum.substring(sum.indexOf(" +") + 3)
        Operator.TIMES -> sum.substring(sum.indexOf(" *") + 3)
    }
}

private fun multiply(digit: Long, sum: String): Pair<Long, String> {
    if(!sum.contains(" ")) {
        return digit * sum.toLong() to ""
    }
    val (otherDigit, rest) = sum.splitIntoTwo(" ")
    return digit * otherDigit.toLong() to rest
}

private fun sum(digit: Long, sum: String): Pair<Long, String> {
    if(!sum.contains(" ")) {
        return digit + sum.toLong() to ""
    }
    val (otherDigit, rest) = sum.splitIntoTwo(" ")
    return digit + otherDigit.toLong() to rest
}

private fun findFirstOperator(sum: String): Operator? {
    val firstPlus = sum.indexOfFirst { it == '+' }
    val firstTimes = sum.indexOfFirst { it == '*' }
    if (firstPlus >= 0 && (firstTimes == -1 ||firstPlus < firstTimes )) {
        return Operator.PLUS
    } else if (firstTimes != -1) {
        return Operator.TIMES
    } else {
        return null;
    }
}