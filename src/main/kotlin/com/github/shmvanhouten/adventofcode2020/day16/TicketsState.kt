package com.github.shmvanhouten.adventofcode2020.day16

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo
import com.github.shmvanhouten.adventofcode2020.util.blocks

data class TicketsState(val rules: List<Rule>, val myTicket: Ticket, val tickets: List<Ticket>) {

    fun listInvalidValues(input: TicketsState): List<Int> {
        return input.tickets.flatMap { ticket ->
            ticket.fields.filter { it.doesNotMatchAnyRule(input.rules) }
        }
    }

    fun withoutInvalidTickets(): TicketsState {
        return this.copy(tickets = tickets.filter { it.isValid(rules) })
    }

    fun matchMyTicketFields(): List<Pair<String, Int>> {
        return this.withoutInvalidTickets().matchTicketFields()
    }

    private fun matchTicketFields(): List<Pair<String, Int>> {
        return matchFieldsByIndex(tickets + myTicket)
            .map { entry ->
                entry.value to myTicket[entry.key]
            }
    }

    private fun matchFieldsByIndex(tickets: List<Ticket>): Map<Int, String> {
        val possibleIndexesToRule = rules.map { rule ->
            findIndicesWhereAllTicketsMatchRule(tickets, rule) to rule.name
        }

        return deDuplicateIndices(possibleIndexesToRule)
    }

    private fun deDuplicateIndices(possibleIndexesByRule: List<Pair<List<Int>, String>>): Map<Int, String> {
        var definitivelyMappedFields = possibleIndexesByRule.filter { isDefinitive(it) }
        var unresolvedFields = possibleIndexesByRule.filter { isUnresolved(it) }

        while (unresolvedFields.isNotEmpty()) {
            unresolvedFields = removeTakenIndices(unresolvedFields, getTakenIndices(definitivelyMappedFields))

            val newlyResolvedFields = unresolvedFields.filter { isDefinitive(it) }

            unresolvedFields -= newlyResolvedFields
            definitivelyMappedFields += newlyResolvedFields
        }
        return definitivelyMappedFields
            .map { it.first[0] to it.second }
            .toMap()
    }

    private fun isUnresolved(it: Pair<List<Int>, String>) = !isDefinitive(it)

    private fun isDefinitive(it: Pair<List<Int>, String>) = it.first.size == 1

    private fun removeTakenIndices(
        unresolvedFields: List<Pair<List<Int>, String>>,
        takenIndices: List<Int>
    ): List<Pair<List<Int>, String>> = unresolvedFields.map { removeTakenIndices(it, takenIndices) }

    private fun getTakenIndices(definitivelyMappedFields: List<Pair<List<Int>, String>>) =
        definitivelyMappedFields.map { it.first }.map { it[0] }

    private fun removeTakenIndices(entry: Pair<List<Int>, String>, takenIndices: List<Int>): Pair<List<Int>, String> {
        val (indices, name) = entry
        return indices - takenIndices to name
    }

    private fun findIndicesWhereAllTicketsMatchRule(tickets: List<Ticket>, rule: Rule): List<Int> {
        return 0.until(tickets[0].fields.size).filter { index ->
            tickets.map { it[index] }.all { rule.matches(it) }
        }
    }
}

data class Rule(val name: String, val ranges: Pair<IntRange, IntRange>) {
    fun matches(target: Int): Boolean {
        return ranges.eitherContains(target)
    }
}

data class Ticket(val fields: List<Int>) {
    fun isValid(rules: List<Rule>): Boolean {
        return this.fields.all { it.matchesARule(rules) }
    }

    operator fun get(i: Int): Int {
        return fields[i]
    }
}

private fun Int.doesNotMatchAnyRule(
    rules: List<Rule>
) = !matchesARule(rules)

private fun Int.matchesARule(
    rules: List<Rule>
) = rules.any { it.ranges.eitherContains(this) }

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
