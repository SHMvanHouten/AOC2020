package com.github.shmvanhouten.adventofcode2020.day19

import java.util.*

class MessageValidatorPt1(unparsedRules: List<String>) {

    private val rules: Map<Int, Rule> = parseRules(unparsedRules)

    fun filterValid(messages: List<String>): Collection<String> {
        val unfinishedMessages: Queue<Pair<ReferenceRule, String>> = LinkedList(listOf(rules[0]!! as ReferenceRule to ""))
        val unmatchedMessages: MutableSet<String> = messages.toMutableSet()
        val validMessages = mutableListOf<String>()

        while (unfinishedMessages.isNotEmpty()) {
            val (remainingRule, message) = unfinishedMessages.poll()!!

            when (val firstReferencedRule = rules[remainingRule.references.first()]) {
                is FinalRule -> {
                    val updatedMessage = message + firstReferencedRule.value
                    if (remainingRule.references.size == 1) {
                        if (unmatchedMessages.contains(updatedMessage)) {
                            validMessages += updatedMessage
                            unmatchedMessages.remove(updatedMessage)
                        }
                    } else {
                        val regex = Regex("^$updatedMessage.*")
                        if(unmatchedMessages.any { regex.matches(it) }) {
                            unfinishedMessages.offer(ReferenceRule(references = remainingRule.references.tail()) to updatedMessage)
                        }
                    }
                }
                is ReferenceRule -> {
                    unfinishedMessages.offer(ReferenceRule(references = firstReferencedRule.references + remainingRule.references.tail()) to message)
                    if (firstReferencedRule.otherReferences != null) {
                        unfinishedMessages.offer(ReferenceRule(references = firstReferencedRule.otherReferences + remainingRule.references.tail()) to message)
                    }
                }
            }
        }
        return validMessages
    }
}

private fun <E> List<E>.tail(): List<E> {
    return this.subList(1, this.size)
}
