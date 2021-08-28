package com.github.shmvanhouten.adventofcode2020.day19

import java.util.*

class MessageValidator(unparsedRules: List<String>) {

    private val rules: Map<Int, Rule> = parseRules(unparsedRules)
    private val allowed = listAllowedStrings()

    fun filterValid(messages: List<String>): List<String> {
        return messages.filter { isValid(it) }
    }

    private fun isValid(message: String): Boolean {

        return allowed.contains(message)
    }

    private fun listAllowedStrings(): Set<String> {
        return expandMessagesFromRule()
    }

    private fun expandMessagesFromRule(): Set<String> {
//        val unfinishedMessages: Queue<Pair<Rule, String>> = LinkedList(rules.values.map { it to "" })
        val unfinishedMessages: Queue<Pair<Rule, String>> = LinkedList(listOf(rules[0]!! to ""))
        val validMessages = mutableSetOf<String>()

        while (unfinishedMessages.isNotEmpty()) {
            val (remainingRule, message) = unfinishedMessages.poll()!!
            when (remainingRule) {
                is FinalRule -> {
                    validMessages += message + remainingRule.value
                }
                is ReferenceRule -> {
                    val firstReferencedRule = rules[remainingRule.references.first()]
                    when(firstReferencedRule) {
                        is FinalRule -> {
                            if(remainingRule.references.size == 1) {
                                validMessages += message + firstReferencedRule.value
                            } else {
                                unfinishedMessages.offer(ReferenceRule(references = remainingRule.references.tail()) to message + firstReferencedRule.value)
                            }
                        }
                        is ReferenceRule -> {
                            unfinishedMessages.offer(ReferenceRule(references = firstReferencedRule.references + remainingRule.references.tail()) to message)
                            if(firstReferencedRule.otherReferences != null) {
                                unfinishedMessages.offer(ReferenceRule(references = firstReferencedRule.otherReferences + remainingRule.references.tail()) to message)
                            }
                        }
                    }
                }
            }
        }
        return validMessages
    }

    private fun parseRules(unparsedRules: List<String>): Map<Int, Rule> {
        return unparsedRules.map { toRule(it) }.associateBy { it.ruleNumber }
    }
}

private fun <E> List<E>.tail(): List<E> {
    return this.subList(1, this.size)
}
