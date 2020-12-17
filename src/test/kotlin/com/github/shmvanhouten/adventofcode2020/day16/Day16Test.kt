package com.github.shmvanhouten.adventofcode2020.day16

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.allElements
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isEmpty
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day16Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `parse the raw input`() {
            val input = """
                |departure location: 37-479 or 485-954
                |departure station: 35-653 or 668-974
                |
                |your ticket:
                |97,101,149,103,137,61,59,223,263,179,131,113,241,127,53,109,89,173,107,211
                |
                |nearby tickets:
                |446,499,748,453,135,109,525,721,179,796,622,944,175,303,882,287,177,185,828,423
                |875,895,652,511,224,634,100,622,677,415,223,620,57,841,511,532,476,716,887,674
            """.trimMargin()

            assertThat(
                parse(input), equalTo(
                    TicketsState(
                        listOf(
                            Rule("departure location", 37..479 to 485..954),
                            Rule("departure station", 35..653 to 668..974)
                        ),
                        Ticket(listOf(97,101,149,103,137,61,59,223,263,179,131,113,241,127,53,109,89,173,107,211)),
                        listOf(
                            Ticket(listOf(446,499,748,453,135,109,525,721,179,796,622,944,175,303,882,287,177,185,828,423)),
                            Ticket(listOf(875,895,652,511,224,634,100,622,677,415,223,620,57,841,511,532,476,716,887,674))
                        )
                    )
                )
            )
        }

        @Nested
        inner class Ignoring_my_ticket {
            @Test
            internal fun `4 is in range of 3-6`() {
                val input = TicketsState(
                    listOf(Rule("a", 3..6 to 3..6)),
                    Ticket(emptyList()),
                    listOf(
                        Ticket(listOf(4))
                    )
                )

                assertThat(listInvalidValues(input), isEmpty)
            }

            @Test
            internal fun `4 is not in range of 5-6`() {
                val input = TicketsState(
                    listOf(Rule("a", 5..6 to 5..6)),
                    Ticket(emptyList()),
                    listOf(
                        Ticket(listOf(4))
                    )
                )

                assertThat(listInvalidValues(input), equalTo(listOf(4)))
            }

            @Test
            internal fun `4 is not in range of 5-6 but is in range of 1-5`() {
                val input = TicketsState(
                    listOf(Rule("a", 5..6 to 1..5)),
                    Ticket(emptyList()),
                    listOf(
                        Ticket(listOf(4))
                    )
                )

                assertThat(listInvalidValues(input), isEmpty)
            }

            @Test
            internal fun `4 is not in range of 5-6 or 7-8, but is in range of 1-5 `() {
                val input = TicketsState(
                    listOf(
                        Rule("a", 5..6 to 7..8),
                        Rule("a", 1..5 to 1..5)
                    ),
                    Ticket(emptyList()),
                    listOf(
                        Ticket(listOf(4))
                    )
                )

                assertThat(listInvalidValues(input), isEmpty)
            }

            @Test
            internal fun `4 is not in range of 5-6 but 5 is`() {
                val input = TicketsState(
                    listOf(Rule("a", 5..6 to 5..6)),
                    Ticket(emptyList()),
                    listOf(
                        Ticket(listOf(5)),
                        Ticket(listOf(4))
                    )
                )

                assertThat(listInvalidValues(input), equalTo(listOf(4)))
            }

            @Test
            internal fun `test input`() {
                val notes = """
                    class: 1-3 or 5-7
                    row: 6-11 or 33-44
                    seat: 13-40 or 45-50

                    your ticket:
                    7,1,14

                    nearby tickets:
                    7,3,47
                    40,4,50
                    55,2,20
                    38,6,12
                """.trimIndent()
                val input = parse(notes)
                assertThat(listInvalidValues(input), equalTo(listOf(4, 55, 12)))
                assertThat(listInvalidValues(input).sum(), equalTo(71))
            }

            @Test
            internal fun `part 1`() {
                val input = parse(readFile("/input-day16.txt"))
                assertThat(listInvalidValues(input).sum(), equalTo(20013))
            }
        }
    }

    @Nested
    inner class Part2 {
        @Test
        internal fun `tickets with invalid fields are discarded entirely`() {
            val notes = """
                    class: 1-3 or 5-7
                    row: 6-11 or 33-44
                    seat: 13-40 or 45-50

                    your ticket:
                    7,1,14

                    nearby tickets:
                    7,3,47
                    40,4,50
                    55,2,20
                    38,6,12
                """.trimIndent()
            val ticketsState = TicketsState(
                rules = listOf(
                    Rule("class", 1..3 to 5..7),
                    Rule("row", 6..11 to 33..44),
                    Rule("seat", 13..40 to 45..50)
                ),
                myTicket = Ticket(listOf(7,1,14)),
                tickets = listOf(
                    Ticket(listOf(7,3,47)),
                    Ticket(listOf(40,4,50)),
                    Ticket(listOf(55,2,20)),
                    Ticket(listOf(38,6,12))
                )

            )
            assertThat(ticketsState.withoutInvalidTickets().tickets(), equalTo(
                listOf(Ticket(listOf(7,3,47)))
            )
        }
    }

}
