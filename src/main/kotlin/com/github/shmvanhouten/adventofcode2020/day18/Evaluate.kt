package com.github.shmvanhouten.adventofcode2020.day18

import com.github.shmvanhouten.adventofcode2020.day18.Operator.PLUS
import com.github.shmvanhouten.adventofcode2020.day18.Operator.TIMES

private const val ZERO_ASCII = '0'.toLong()

fun evaluate(input: String): Long {
    var expression = input.filter { it != ' ' }
    var i = 0
    var result: Long? = null
    var operator = PLUS
    while (i < expression.length) {
        val nextChar = expression[i]
        when(nextChar) {
            '+' -> operator = PLUS
            '*' -> operator = TIMES
            '(' -> {
                val (block, expressionWithoutBlock) = extractBlockFromExpression(expression)
                expression = expressionWithoutBlock
                if(result == null) result = evaluate(block)
                else result = operator.applyTo(result, evaluate(block))
            }
            else -> {
                if(result == null) result = toLong(nextChar)
                else result = operator.applyTo(result, toLong(nextChar))
            }
        }

        i++
    }
    return result!!
}

private fun extractBlockFromExpression(expression: String): Pair<String, String> {
    val block = extractBlock(expression)
    val expressionWithoutBlock = expression
        .removeRange(expression.indexOf('('), expression.indexOf('(') + block.length + 1)
    return Pair(block, expressionWithoutBlock)
}

fun extractBlock(expression: String): String {
    val stringFromOpeningBracket = expression.substring(expression.indexOf('(') + 1)
    var amountOfOpeningBrackets = 1
    var amountOfClosingBrackets = 0
    var index = 0
    stringFromOpeningBracket
        .asSequence()
        .takeWhile { amountOfClosingBrackets < amountOfOpeningBrackets }
        .forEachIndexed { i, c ->
            index = i
            when(c) {
                '(' -> amountOfOpeningBrackets++
                ')' -> amountOfClosingBrackets++
            }
        }
    return stringFromOpeningBracket.substring(0, index)
}

enum class Operator {
    PLUS,
    TIMES;

    fun applyTo(result: Long, other: Long): Long {
        return when(this) {
            PLUS -> result + other
            TIMES -> result * other
        }
    }
}

private fun toLong(it: Char): Long {
    return it.toLong() - ZERO_ASCII
}