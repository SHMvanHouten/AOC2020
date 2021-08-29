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

            when (val firstReferencedRule = remainingRule.firstReferencedRule(rules)) {
                is FinalRule -> {
                    val updatedMessage = message + firstReferencedRule.value
                    if (remainingRule.references.size > 1) {
                        val regex = Regex("^$updatedMessage.*")
                        if (unmatchedMessages.any { regex.matches(it) }) {
                            unfinishedMessages.offer(remainingRule.withoutFirstReference() to updatedMessage)
                        }

                    } else if (unmatchedMessages.contains(updatedMessage)) {
                        move(updatedMessage)
                            .from(unmatchedMessages)
                            .to(validMessages)
                    }

                }
                is ReferenceRule -> {
                    unfinishedMessages.offer(
                        remainingRule.replaceFirstReferenceWith(firstReferencedRule.references) to message
                    )
                    firstReferencedRule.otherReferences
                        ?.let { remainingRule.replaceFirstReferenceWith(it) }
                        ?.let { unfinishedMessages.offer(it to message) }
                }
            }
        }
        return validMessages
    }

    private fun <T> move(element: T): ElementMoveDsl<T> {
        return ElementMoveDsl(element)
    }
}

class ElementMoveDsl<T>(private val element: T) {

    fun to(collection: MutableCollection<T>) {
        collection.add(element)
    }

    fun from(collection: MutableCollection<T>): ElementMoveDsl<T> {
        collection.remove(element)
        return this
    }
}

private fun <E> List<E>.tail(): List<E> {
    return this.subList(1, this.size)
}
