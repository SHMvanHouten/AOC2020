package com.github.shmvanhouten.adventofcode2020.day19

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo
import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.github.shmvanhouten.adventofcode2020.util.blocks
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day19Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `rule 0 is a matches a`() {
            val (rules, inputs) = splitRulesAndInputs(
                """
                                    |0: "a"
                                    |
                                    |a
                                """.trimMargin()
            )
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(inputs), hasSize(equalTo(1)))
        }

        @Test
        internal fun `rule 0 is a does not match b`() {
            val (rules, inputs) = splitRulesAndInputs(
                """
                                    |0: "a"
                                    |
                                    |b
                                """.trimMargin()
            )
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(inputs), isEmpty)
        }

        @Test
        internal fun `rule 0 is b matches b`() {
            val (rules, inputs) = splitRulesAndInputs(
                """
                                    |0: "b"
                                    |
                                    |b
                                """.trimMargin()
            )
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(inputs), hasSize(equalTo(1)))
        }

        @Test
        internal fun `rule 0 concats 1 - a 2 - b`() {
            val (rules, inputs) = splitRulesAndInputs(
                """0: 1 2
1: "a"
2: "b"

ab"""
            )
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(inputs), hasSize(equalTo(1)))
        }

        @Test
        internal fun `rule 0 concats 1 - b 2 - a`() {
            val (rules, inputs) = splitRulesAndInputs(
                """0: 1 2
1: "b"
2: "a"

ba
ab"""
            )
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(inputs), hasSize(equalTo(1)))
            assertThat(validator.filterValid(inputs)[0], equalTo("ba"))
        }

        @Test
        internal fun `rule 0 matches either a or b`() {
            val (rules, inputs) = splitRulesAndInputs(
                """0: 1 | 2
1: "b"
2: "a"

ba
ab
a
b"""
            )
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(inputs), hasSize(equalTo(2)))
            assertThat(validator.filterValid(inputs), equalTo(listOf("a", "b")))
        }

        @Test
        internal fun `example 1 rule matches aab and aba`() {
            val (rules, messages) = splitRulesAndInputs("""0: 1 2
1: "a"
2: 1 3 | 3 1
3: "b"

aab
aaa
aba
baa""")
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(messages), hasSize(equalTo(2)))
            assertThat(validator.filterValid(messages), equalTo(listOf("aab", "aba")))
        }

        @Test
        internal fun `example 2`() {
            val (rules, messages) = splitRulesAndInputs("""0: 4 1 5
1: 2 3 | 3 2
2: 4 4 | 5 5
3: 4 5 | 5 4
4: "a"
5: "b"

ababbb
bababa
abbbab
aaabbb
aaaabbb""")
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(messages), hasSize(equalTo(2)))
            assertThat(validator.filterValid(messages), equalTo(listOf("ababbb", "abbbab")))
        }

        @Test
        internal fun `part 1`() {
            val (rules, messages) = splitRulesAndInputs(readFile("/input-day19.txt"))
            val validator = MessageValidator(rules)

            assertThat(validator.filterValid(messages), hasSize(equalTo(2)))
        }
    }

    private fun splitRulesAndInputs(input: String): Pair<List<String>, List<String>> {
        val blocks = input.blocks()
//        val rules = parseRules(blocks[0])
        val inputs = blocks[1].lines()
        return blocks[0].lines() to inputs
    }

    private fun parseRules(rulesInput: String) = rulesInput.lines()
        .map { it.splitIntoTwo(": ") }
        .sortedBy { it.first.toInt() }
        .map { it.second }

}


