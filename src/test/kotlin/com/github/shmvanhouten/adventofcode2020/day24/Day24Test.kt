package com.github.shmvanhouten.adventofcode2020.day24

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class Day24Test {
    @Nested
    inner class Part1 {
        @ParameterizedTest(name = "{0} flips [{1},{2}]")
        @CsvSource(
            value = [
                "e, 1, 0",
                "w, -1, 0",
                "ne, 1, -1",
                "se, 1, 1"
            ]
        )
        internal fun `following the path flips the hex at destination`(
            input: String,
            x: Int,
            y: Int
        ) {
            val floor = Floor(input)
            assertThat(floor.blackTiles.size, equalTo(1))
            assertThat(floor.blackTiles.first(), equalTo(HexCoordinate(x, y)))
        }

        @Test
        internal fun `moving east twice flips the tile at 2,0`() {
            val floor = Floor("ee")
            assertThat(floor.blackTiles.size, equalTo(1))
            assertThat(floor.blackTiles.first(), equalTo(HexCoordinate(2, 0)))
        }

        @Test
        internal fun `two instructions, 1 moving east flips (1,0), 1 moving west flips (-1,0)`() {
            val floor = Floor("e\nw")
            assertThat(floor.blackTiles.size, equalTo(2))
            assertThat(floor.blackTiles.toSet(), equalTo(setOf(HexCoordinate(1, 0), HexCoordinate(-1, 0))))
        }

        @Test
        internal fun `two instructions, both moving east 1 step, flips the tile on and off again`() {
            val floor = Floor("e\ne")
            assertThat(floor.blackTiles.size, equalTo(0))
        }

        @Test
        internal fun `example 1`() {
            val floor = Floor(example)
            assertThat(floor.countBlackTiles(), equalTo(10))
        }

        @Test
        internal fun `part 1`() {
            val input = readFile("/input-day24.txt")
            val floor = Floor(input)
            assertThat(floor.blackTiles.size, equalTo(382))
        }

    }

    @Nested
    inner class Part_2_game_of_life_hex_style {

        @ParameterizedTest(name = "Day {0}: {1}")
        @CsvSource(
            value = [
                "1, 15",
                "2, 12",
                "3, 25",
                "4, 14",
                "5, 23",
                "6, 28",
                "7, 41",
                "8, 37",
                "9, 49",
                "10, 37",
                "20, 132",
                "30, 259",
                "40, 406",
                "50, 566",
                "60, 788",
                "70, 1106",
                "80, 1373",
                "90, 1844",
                "100, 2208"
            ]
        )
        internal fun `after x days y tiles are black`(
            days: Int,
            expectedBlackTiles: Int
        ) {
            val floor = Floor(example)
            assertThat(floor.countBlackTiles(), equalTo(10))

            val flippedFloor = tick(floor, days)
            assertThat(flippedFloor.countBlackTiles(), equalTo(expectedBlackTiles))
        }

        @Test
        internal fun `part 2`() {
            val input = readFile("/input-day24.txt")
            val floor = Floor(input)
            val flippedFloor = tick(floor, 100)

            assertThat(flippedFloor.countBlackTiles(), equalTo(3964))
        }
    }

}

private val example = """
                sesenwnenenewseeswwswswwnenewsewsw
                neeenesenwnwwswnenewnwwsewnenwseswesw
                seswneswswsenwwnwse
                nwnwneseeswswnenewneswwnewseswneseene
                swweswneswnenwsewnwneneseenw
                eesenwseswswnenwswnwnwsewwnwsene
                sewnenenenesenwsewnenwwwse
                wenwwweseeeweswwwnwwe
                wsweesenenewnwwnwsenewsenwwsesesenwne
                neeswseenwwswnwswswnw
                nenwswwsewswnenenewsenwsenwnesesenew
                enewnwewneswsewnwswenweswnenwsenwsw
                sweneswneswneneenwnewenewwneswswnese
                swwesenesewenwneswnwwneseswwne
                enesenwswwswneneswsenwnewswseenwsese
                wnwnesenesenenwwnenwsewesewsesesew
                nenewswnwewswnenesenwnesewesw
                eneswnwswnwsenenwnwnwwseeswneewsenese
                neswnwewnwnwseenwseesewsenwsweewe
                wseweeenwnesenwwwswnew
            """.trimIndent()
