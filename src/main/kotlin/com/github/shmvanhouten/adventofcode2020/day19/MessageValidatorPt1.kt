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

            if (rule.otherReferences != null) {
                splitUpRule(unfinishedMessages, rule)

            } else {
                val updatedRule = rule.mergeFirstReference(rules)
                if (shouldProcessRuleFurther(updatedRule, rule, unmatchedMessages)) {
                        unfinishedMessages.offer(updatedRule)

                } else if (unmatchedMessages.contains(updatedRule.message)) {
                    move(updatedRule.message)
                        .from(unmatchedMessages)
                        .to(validMessages)
                }
            }
        }
        return validMessages
    }

    private fun splitUpRule(
        unfinishedMessages: Queue<ReferenceRule>,
        rule: ReferenceRule
    ) {
        unfinishedMessages.offer(rule.copy(otherReferences = null))
        unfinishedMessages.offer(rule.copy(references = rule.otherReferences!!, otherReferences = null))
    }

    private fun shouldProcessRuleFurther(
        updatedRule: ReferenceRule,
        rule: ReferenceRule,
        unmatchedMessages: MutableSet<String>
    ) = (updatedRule.references.isNotEmpty()
            && messageMatchIsStillPossible(rule, updatedRule, unmatchedMessages))

    private fun messageMatchIsStillPossible(
        rule: ReferenceRule,
        updatedRule: ReferenceRule,
        unmatchedMessages: MutableSet<String>
    ) = rule.message == updatedRule.message || messageIsStillViable(unmatchedMessages, updatedRule.message)

    private fun messageIsStillViable(
        unmatchedMessages: MutableSet<String>,
        message: String
    ): Boolean {
        val regex = Regex("^$message.*")
        return unmatchedMessages.any { regex.matches(it) }
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
