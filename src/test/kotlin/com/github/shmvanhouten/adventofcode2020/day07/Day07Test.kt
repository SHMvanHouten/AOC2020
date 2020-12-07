package com.github.shmvanhouten.adventofcode2020.day07

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day07Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun parse() {
            val input = "light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
                    "faded blue bags contain no other bags."
            assertThat(parseBags(input), equalTo(
                listOf(
                    Bag("light red", mapOf("bright white" to 1, "muted yellow" to 2)),
                    Bag("faded blue", emptyMap())
                )
            ) )
        }
    }

}

val testInput = """light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags."""
