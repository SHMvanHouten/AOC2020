package com.github.shmvanhouten.adventofcode2020.day19

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

interface Rule {
    val message: String
}

data class FinalRule(override val message: String) : Rule
data class ReferenceRule(
    val references: List<Int>,
    val otherReferences: List<Int>? = null,
    override val message: String = ""
) : Rule {

    fun firstReferencedRule(rules: Map<Int, Rule>): Rule {
        return rules[references.first()]
            ?: throw Error("Reference ${references.first()} not found in ruleset $rules")
    }

    fun mergeFirstReference(rules: Map<Int, Rule>): ReferenceRule {
        return when(val firstReferencedRule = firstReferencedRule(rules)) {
            is FinalRule -> this.copy(
                references = references.tail(),
                message = message + firstReferencedRule.message
            )
            is ReferenceRule -> this.copy(
                references = firstReferencedRule.references + references.tail(),
                otherReferences = firstReferencedRule.otherReferences?.plus(references.tail())
            )
            else -> throw Error()
        }
    }
}

fun toNumberedRule(unparsedRule: String): Pair<Int, Rule> {
    return extractRuleNumber(unparsedRule) to
            if (unparsedRule.contains("\"")) {
                FinalRule(unparsedRule.substringAfter(": ")[1].toString())
            } else {
                toReferenceRule(unparsedRule)
            }
}

fun toReferenceRule(unparsedRule: String): Rule {
    return if (unparsedRule.contains('|')) {
        toConditionalRule(unparsedRule)
    } else {
        ReferenceRule(
            parseRuleReferences(unparsedRule)
        )
    }
}

fun toConditionalRule(unparsedRule: String): Rule {
    val (ruleReferences1, ruleReferences2) = unparsedRule.splitIntoTwo(" | ")
    return ReferenceRule(
        parseRuleReferences(ruleReferences1),
        parseRuleReferences(ruleReferences2)
    )
}

fun parseRules(unparsedRules: List<String>): Map<Int, Rule> {
    return unparsedRules.associate { toNumberedRule(it) }
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
