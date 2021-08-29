package com.github.shmvanhouten.adventofcode2020.day19

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

open class Rule(val ruleNumber: Int)

data class FinalRule(val number: Int, val value: Char) : Rule(number)
data class ReferenceRule(val number: Int = -1, val references: List<Int>, val otherReferences: List<Int>? = null) : Rule(number) {
    fun hasDivergingPaths(): Boolean {
        return this.otherReferences != null
    }

    fun withoutFirstReference(): ReferenceRule {
        return ReferenceRule(references = this.references.tail())
    }

    fun replaceFirstReferenceWith(otherReferences: List<Int>): ReferenceRule {
        return ReferenceRule(references = otherReferences + this.references.tail())
    }

    fun firstReferencedRule(rules: Map<Int, Rule>): Rule {
        return rules[references.first()]
            ?: throw Error("Reference ${references.first()} not found in ruleset $rules")
    }
}

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

fun parseRules(unparsedRules: List<String>): Map<Int, Rule> {
    return unparsedRules.map { toRule(it) }.associateBy { it.ruleNumber }
}

fun parseRuleReferences(rule: String): List<Int> {
    return rule
        .substringAfter(": ")
        .trim()
        .split(" ")
        .map(String::toInt)
}

private fun extractRuleNumber(unparsedRule: String) = unparsedRule.substringBefore(':').toInt()

private fun <E> List<E>.tail(): List<E> {
    return this.subList(1, this.size)
}
