package com.github.shmvanhouten.adventofcode2020.day19

import java.util.*

class MessageValidatorPt1(unparsedRules: List<String>) {

    private val rules: Map<Int, Rule> = parseRules(unparsedRules)

    fun filterValid(messages: List<String>): Collection<String> {
        val unfinishedMessages: Queue<ReferenceRule> = LinkedList(listOf(rules[0]!! as ReferenceRule))
        val unmatchedMessages: MutableSet<String> = messages.toMutableSet()
        val validMessages = mutableListOf<String>()

        while (unfinishedMessages.isNotEmpty()) {
            val rule = unfinishedMessages.poll()!!

            when (val firstReferencedRule = rule.firstReferencedRule(rules)) {
                is FinalRule -> {
                    val updatedRule = rule.mergeFirstReference(rules)
                    if (updatedRule.references.isNotEmpty()) {
                        val regex = Regex("^${updatedRule.message}.*")
                        if (unmatchedMessages.any { regex.matches(it) }) {
                            unfinishedMessages.offer(updatedRule)
                        }

                    } else if (unmatchedMessages.contains(updatedRule.message)) {
                        move(updatedRule.message)
                            .from(unmatchedMessages)
                            .to(validMessages)
                    }

                }
                is ReferenceRule -> {
                    unfinishedMessages.offer(
                        rule.replaceFirstReferenceWith(firstReferencedRule.references)
                    )
                    firstReferencedRule.otherReferences
                        ?.let { rule.replaceFirstReferenceWith(it) }
                        ?.let { unfinishedMessages.offer(it) }
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
