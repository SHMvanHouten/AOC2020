package com.github.shmvanhouten.adventofcode2020.day19

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

open class Rule(val ruleNumber: Int)

data class FinalRule(val number: Int, val value: Char) : Rule(number)
data class ReferenceRule(val number: Int = -1, val references: List<Int>, val otherReferences: List<Int>? = null) : Rule(number)

fun toRule(unparsedRule: String): Rule {
    return if (unparsedRule.contains("\"")) {
        FinalRule(extractRuleNumber(unparsedRule), unparsedRule.substringAfter(": ")[1])
    } else {
        toReferenceRule(unparsedRule)
    }
}

fun toReferenceRule(unparsedRule: String): Rule {
    return if (unparsedRule.contains('|')) {
        toConditionalRule(unparsedRule)
    } else {
        ReferenceRule(
            extractRuleNumber(unparsedRule),
            parseRuleReferences(unparsedRule)
        )
    }
}

fun toConditionalRule(unparsedRule: String): Rule {
    val (ruleReferences1, ruleReferences2) = unparsedRule.splitIntoTwo(" | ")
    return ReferenceRule(
        extractRuleNumber(unparsedRule),
        parseRuleReferences(ruleReferences1),
        parseRuleReferences(ruleReferences2)
    )
}

fun parseRuleReferences(rule: String): List<Int> {
    return rule
        .substringAfter(": ")
        .trim()
        .split(" ")
        .map(String::toInt)
}

private fun extractRuleNumber(unparsedRule: String) = unparsedRule.substringBefore(':').toInt()
