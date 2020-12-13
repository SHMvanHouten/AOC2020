package com.github.shmvanhouten.adventofcode2020.day07

import com.github.shmvanhouten.adventofcode2020.util.FileReader
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
                    "bright white bags contain 1 shiny gold bag.\n" +
                    "faded blue bags contain no other bags."
            assertThat(parseBags(input), equalTo(
                listOf(
                    Bag("light red", mapOf("bright white" to 1, "muted yellow" to 2)),
                    Bag("bright white", mapOf("shiny gold" to 1)),
                    Bag("faded blue", emptyMap())
                )
            ) )
        }

        @Test
        internal fun `test input`() {
            assertThat(countBagsThatCanContain(parseBags(testInput), "shiny gold"), equalTo(4L))
        }

        @Test
        internal fun `part 1`() {
            val input = FileReader.readFile("/input-day07.txt")
            assertThat(
                countBagsThatCanContain(parseBags(input), "shiny gold"),
                equalTo(185L)
            )
        }
    }

    @Nested
    inner class Part2 {
        @Test
        internal fun `test input`() {
            assertThat(countAmountOfBagsItHolds(parseBags(testInput), "shiny gold"), equalTo(32L))
        }

        @Test
        internal fun `other test input`() {
            assertThat(countAmountOfBagsItHolds(parseBags(testInput2), "shiny gold"), equalTo(126L))
        }

        @Test
        internal fun `part 2`() {
            val input = FileReader.readFile("/input-day07.txt")
            assertThat(
                countAmountOfBagsItHolds(parseBags(input), "shiny gold"),
                equalTo(89084L)
            )
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

val testInput2 = """shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags."""
