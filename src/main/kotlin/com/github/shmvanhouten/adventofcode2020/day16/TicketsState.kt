package com.github.shmvanhouten.adventofcode2020.day16

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

data class TicketsState(val rules: List<Rule>, val myTicket: Ticket, val tickets: List<Ticket>) {
    fun withoutInvalidTickets(): TicketsState {
        return this.copy(tickets = tickets.filter{ it.isValid(rules) })
    }

    fun matchMyTicketFields(): List<Pair<String, Int>> {
        return this.withoutInvalidTickets().matchTicketFields()
    }

    private fun matchTicketFields(): List<Pair<String,Int>> {
        val fieldsByIndex: Map<Int, String> = matchFieldsByIndex(tickets + myTicket)
        return fieldsByIndex.map { entry ->
            entry.value to myTicket[entry.key]
        }
    }

    private fun matchFieldsByIndex(tickets: List<Ticket>): Map<Int, String> {
        val possibleIndexesByRule = rules.map { rule ->
            rule.name to findIndexWhereAllTicketsMatchRule(tickets, rule)
        }
        if(possibleIndexesByRule.all { it.second.size == 1 }) {
            return possibleIndexesByRule
                .map { it.second[0] to it.first }
                .toMap()
        } else {
            return deDuplicateIndexes(possibleIndexesByRule)
        }
    }

    private fun deDuplicateIndexes(possibleIndexesByRule: List<Pair<String, List<Int>>>): Map<Int, String> {
        val list = possibleIndexesByRule.toMutableList()
        var nextNameToTryToUniquify = list.first { it.second.size > 1 }

        while (list.map { it.second }.any { it.size != 1 }) {
            val (name, indexes) = nextNameToTryToUniquify
            val index = list.indexOf(nextNameToTryToUniquify)
            val indexToRemove = list.asSequence()
                .filter { it.second.size == 1 }
                .map { it.second[0] }
                .find { indexes.contains(it) }
            if(indexToRemove == null) {
                nextNameToTryToUniquify = list.subList(list.indexOf(nextNameToTryToUniquify) + 1, list.size)
                    .first { it.second.size > 1 }
            } else {
                nextNameToTryToUniquify = name to indexes - indexToRemove
                list[index] = nextNameToTryToUniquify
                if(nextNameToTryToUniquify.second.size == 1) {
                    val subList = list.subList(list.indexOf(nextNameToTryToUniquify) + 1, list.size)
                        .filter { it.second.size > 1 }
                    if(subList.isEmpty()) {
                        break
                    }
                    nextNameToTryToUniquify = subList[0]
                }
            }
        }
        return list
            .map { it.second[0] to it.first }
            .toMap()
    }

    private fun findIndexWhereAllTicketsMatchRule(tickets: List<Ticket>, rule: Rule): List<Int> {
        val indexes = 0.until(tickets[0].fields.size).filter { index ->
            tickets.map { it[index] }.all { rule.matches(it) }
        }
//        if (indexes.size != 1) error("expected 1 index to match the rule: $rule, but was $indexes")
        return indexes
    }
}

data class Rule(val name: String, val ranges: Pair<IntRange, IntRange>) {
    fun matches(target: Int): Boolean {
        return ranges.eitherContains(target)
    }
}

data class Ticket(val fields: List<Int>) {
    fun isValid(rules: List<Rule>): Boolean {
        // todo: refactor double negative
        return this.fields.none { it.doesNotMatchAnyRule(rules) }
    }

    operator fun get(i: Int): Int {
        return fields[i]
    }
}

fun listInvalidValues(input: TicketsState): List<Int> {
    // todo: move inside TicketsState
    return input.tickets.flatMap { ticket ->
        ticket.fields.filter { it.doesNotMatchAnyRule(input.rules) }
    }
}

private fun Int.doesNotMatchAnyRule(
    rules: List<Rule>
) = !rules.any { it.ranges.eitherContains(this) }

private fun Pair<IntRange, IntRange>.eitherContains(target: Int): Boolean {
    return this.first.contains(target) || this.second.contains(target)
}

fun parse(input: String): TicketsState {
    val blocks = input.blocks()
    return TicketsState(
        parseRules(blocks[0]),
        parseTicket(blocks[1].lines()[1]),
        parseTickets(blocks[2])
    )
}

fun parseTickets(input: String): List<Ticket> {
    val lines = input.lines()
    return lines.subList(1, lines.size)
        .map { parseTicket(it) }
}

fun parseTicket(raw: String): Ticket {
    return Ticket(raw.split(',').map { it.toInt() })
}

fun parseRules(raw: String): List<Rule> {
    return raw.lines()
        .map { toRule(it) }
}

fun toRule(raw: String): Rule {
    val (name, ranges) = raw.splitIntoTwo(": ")
    return Rule(
        name,
        ranges.splitIntoTwo(" or ").toPairOfRanges()
    )
}

private fun Pair<String, String>.toPairOfRanges(): Pair<IntRange, IntRange> {
    return this.first.toRange() to this.second.toRange()
}

private fun String.toRange(): IntRange {
    val (start, end) = this.splitIntoTwo("-")
    return start.toInt()..end.toInt()
}


private fun String.blocks(): List<String> {
    // todo: move to util
    return this.split("\n\n")
}
