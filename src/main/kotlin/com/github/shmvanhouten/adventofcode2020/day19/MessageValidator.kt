package com.github.shmvanhouten.adventofcode2020.day19

class MessageValidator(private val rules: List<String>) {

    private val allowed = listAllowedStrings()

    fun filterValid(messages: List<String>): List<String> {
        return messages.filter { isValid(it) }
    }

    private fun isValid(message: String): Boolean {

        return allowed.contains(message)
    }

    private fun listAllowedStrings(): Set<String> {
        return expandMessageFromRule(rules[0])
    }

    private fun expandMessageFromRule(rule: String): Set<String> {
        if (rule.contains('\"')) {
            return setOf(rule.substring(1, 2))
        } else if (rule.contains('|')) {
            return rule.split(" | ")
                .flatMap { expandMessageFromRule(it) }
                .toSet()
        } else {
            return rule.split(' ')
                .map { it.toInt() }
                .fold(setOf("")) { messages, index ->
                    expandMessageFromRule(rules[index])
                        .flatMap { messages.map{ m -> m + it } }
                        .toSet()
                }
                .toSet()

        }
    }
}
