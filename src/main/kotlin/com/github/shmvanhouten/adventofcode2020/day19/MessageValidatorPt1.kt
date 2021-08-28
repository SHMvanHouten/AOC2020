package com.github.shmvanhouten.adventofcode2020.day19

import java.util.*

class MessageValidatorPt1(unparsedRules: List<String>) {

    private val rules: Map<Int, Rule> = parseRules(unparsedRules)

    fun filterValid(messages: List<String>): Collection<String> {
        val unfinishedMessages: Queue<Pair<Rule, String>> = LinkedList(listOf(rules[0]!! to ""))
        val unmatchedMessages: MutableSet<String> = messages.toMutableSet()
        val validMessages = mutableListOf<String>()

        while (unfinishedMessages.isNotEmpty()) {
            val (remainingRule, message) = unfinishedMessages.poll()!!
            val regex = Regex("^$message.*")
            if(message.isNotEmpty() && unmatchedMessages.none { regex.matches(it) }) {
                continue
            }
            when (remainingRule) {
                is FinalRule -> {
                    val finalMessage = message + remainingRule.value
                    if(unmatchedMessages.contains(finalMessage)) {
                        validMessages += finalMessage
                        unmatchedMessages.remove(finalMessage)
                    }
                }
                is ReferenceRule -> {
                    when(val firstReferencedRule = rules[remainingRule.references.first()]) {
                        is FinalRule -> {
                            if(remainingRule.references.size == 1) {
                                val finalMessage = message + firstReferencedRule.value
                                if(unmatchedMessages.contains(finalMessage)) {
                                    validMessages += finalMessage
                                    unmatchedMessages.remove(finalMessage)
                                }
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
}

private fun <E> List<E>.tail(): List<E> {
    return this.subList(1, this.size)
}
