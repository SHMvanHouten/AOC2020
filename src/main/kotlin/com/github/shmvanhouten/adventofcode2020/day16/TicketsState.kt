package com.github.shmvanhouten.adventofcode2020.day16

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

data class TicketsState(val rules: List<Rule>, val myTicket: Ticket, val tickets: List<Ticket>) {
    fun withoutInvalidTickets(): TicketsState {
        return this.copy(tickets = tickets.filter{ it.isValid() })
    }
}

data class Rule(val name: String, val ranges: Pair<IntRange, IntRange>)

data class Ticket(val fields: List<Int>) {
    fun isValid(): Boolean {
        this.fields.none {  }
    }
}

fun listInvalidValues(input: TicketsState): List<Int> {
    // todo: move inside TicketsState
    return input.tickets.flatMap {
        it.fields.filter { doesNotMatchAnyRule(input, it) }
    }
}

private fun doesNotMatchAnyRule(
    input: TicketsState,
    target: Int
) = !input.rules.any { it.ranges.eitherContains(target) }

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
