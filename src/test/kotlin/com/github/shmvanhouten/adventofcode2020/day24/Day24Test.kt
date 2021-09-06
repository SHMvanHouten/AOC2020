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
            assertThat(floor.tiles.size, equalTo(1))
            assertThat(floor.tiles.keys.first(), equalTo(HexCoordinate(x,y)))
        }

        @Test
        internal fun `moving east twice flips the tile at 2,0`() {
            val floor = Floor("ee")
            assertThat(floor.tiles.size, equalTo(1))
            assertThat(floor.tiles.keys.first(), equalTo(HexCoordinate(2,0)))
        }

        @Test
        internal fun `two instructions, 1 moving east flips (1,0), 1 moving west flips (-1,0)`() {
            val floor = Floor("e\nw")
            assertThat(floor.tiles.size, equalTo(2))
            assertThat(floor.tiles.keys.toSet(), equalTo(setOf(HexCoordinate(1, 0), HexCoordinate(-1, 0))))
        }

        @Test
        internal fun `two instructions, both moving east 1 step, flips the tile on and off again`() {
            val floor = Floor("e\ne")
            assertThat(floor.tiles.size, equalTo(1))
            assertThat(floor.tiles.keys.toSet(), equalTo(setOf(HexCoordinate(1, 0))))
            assertThat(floor.tiles[HexCoordinate(1,0)], equalTo(false))
        }

        @Test
        internal fun `example 1`() {
            val example = """
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
            val floor = Floor(example)
            assertThat(floor.tiles.size, equalTo(15))
            assertThat(floor.tiles.filter { it.value }.size, equalTo(10))
        }

        @Test
        internal fun `part 1`() {
            val input = readFile("/input-day24.txt")
            val floor = Floor(input)
            assertThat(floor.tiles.filter { it.value }.size, equalTo(382))
        }
    }

}
