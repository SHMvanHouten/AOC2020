package com.github.shmvanhouten.adventofcode2020.day19

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo
import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.github.shmvanhouten.adventofcode2020.util.blocks
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import com.natpryce.hamkrest.isEmpty
import org.junit.Ignore
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
            val validator = MessageValidatorPt1(rules)

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
            val validator = MessageValidatorPt1(rules)

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
            val validator = MessageValidatorPt1(rules)

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
            val validator = MessageValidatorPt1(rules)

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
            val validator = MessageValidatorPt1(rules)

            assertThat(validator.filterValid(inputs), hasSize(equalTo(1)))
            assertThat(validator.filterValid(inputs).first(), equalTo("ba"))
        }

        @Test
        @Ignore("todo: find out why this is failing")
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
            val validator = MessageValidatorPt1(rules)

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
            val validator = MessageValidatorPt1(rules)

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
            val validator = MessageValidatorPt1(rules)

            assertThat(validator.filterValid(messages), hasSize(equalTo(2)))
            assertThat(validator.filterValid(messages), equalTo(listOf("abbbab", "ababbb")))
        }

        @Test
        internal fun `part 1`() {
            val (rules, messages) = splitRulesAndInputs(readFile("/input-day19.txt"))
            val validator = MessageValidatorPt1(rules)

            assertThat(validator.filterValid(messages), hasSize(equalTo(235)))
        }
    }

    @Nested
    inner class Part2 {

        @Test
        internal fun `example part 2`() {
            val (rules, messages) = splitRulesAndInputs("""42: 9 14 | 10 1
9: 14 27 | 1 26
10: 23 14 | 28 1
1: "a"
11: 42 31 | 42 11 31
5: 1 14 | 15 1
19: 14 1 | 14 14
12: 24 14 | 19 1
16: 15 1 | 14 14
31: 14 17 | 1 13
6: 14 14 | 1 14
2: 1 24 | 14 4
0: 8 11
13: 14 3 | 1 12
15: 1 | 14
17: 14 2 | 1 7
23: 25 1 | 22 14
28: 16 1
4: 1 1
20: 14 14 | 1 15
3: 5 14 | 16 1
27: 1 6 | 14 18
14: "b"
21: 14 1 | 1 14
25: 1 1 | 1 14
22: 14 14
8: 42 | 42 8
26: 14 22 | 1 20
18: 15 15
7: 14 5 | 1 21
24: 14 1

abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb
aaaaabbaabaaaaababaa
aaaabbaaaabbaaa
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
babaaabbbaaabaababbaabababaaab
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba""")

            val validator = MessageValidatorPt1(rules)

            assertThat(validator.filterValid(messages), hasSize(equalTo(12)))
        }

        @Test
        internal fun `part 2`() {
            val (rules, messages) = splitRulesAndInputs(readFile("/input-day19-pt2.txt"))

            val validator = MessageValidatorPt1(rules)

            assertThat(validator.filterValid(messages), hasSize(equalTo(379)))
        }
    }

    private fun splitRulesAndInputs(input: String): Pair<List<String>, List<String>> {
        val blocks = input.blocks()
//        val rules = parseRules(blocks[0])
        val inputs = blocks[1].lines()
        return blocks[0].lines() to inputs
    }

}


