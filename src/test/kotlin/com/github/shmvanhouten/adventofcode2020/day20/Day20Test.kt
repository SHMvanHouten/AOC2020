package com.github.shmvanhouten.adventofcode2020.day20

import com.github.shmvanhouten.adventofcode2020.coordinate.Coordinate
import com.github.shmvanhouten.adventofcode2020.coordinate.left
import com.github.shmvanhouten.adventofcode2020.coordinate.right
import com.github.shmvanhouten.adventofcode2020.coordinate.toCoordinateMap
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day20Test {

    @Nested
    inner class Part1 {

        @Test
        internal fun `the tiles line up side by side and top to bottom`() {
            val tiles = """
                |tile 123:
                |##.
                |...
                |.#.
                |
                |tile 456:
                |..#
                |..#
                |...
                |
                |tile 777:
                |.#.
                |...
                |#.#
                |
                |tile 888:
                |...
                |..#
                |#..
                """.trimMargin()
            val tileArrangement = findCornerTiles(tiles)
            assertThat(tileArrangement.top.left().id, equalTo(123))
            assertThat(tileArrangement.top.right().id, equalTo(456))
            assertThat(tileArrangement.bottom.left().id, equalTo(777))
            assertThat(tileArrangement.bottom.right().id, equalTo(888))
        }

        @Test
        internal fun `the tiles still line up with last two tiles switched positions`() {
            val tiles = """
                |tile 123:
                |##.
                |...
                |.#.
                |
                |tile 456:
                |..#
                |..#
                |...
                |
                |tile 888:
                |...
                |..#
                |#..
                |
                |tile 777:
                |.#.
                |...
                |#.#
                """.trimMargin()
            val tileArrangement = findCornerTiles(tiles)
            assertThat(tileArrangement.top.left().id, equalTo(123))
            assertThat(tileArrangement.top.right().id, equalTo(456))
            assertThat(tileArrangement.bottom.left().id, equalTo(777))
            assertThat(tileArrangement.bottom.right().id, equalTo(888))
        }

        @Test
        internal fun `the tiles still line up with first and last tile switched positions`() {
            val tiles = """
                |tile 777:
                |##.
                |#..
                |###
                |
                |tile 456:
                |..#
                |..#
                |...
                |
                |tile 888:
                |...
                |..#
                |#..
                |
                |tile 123:
                |##.
                |...
                |##.
                """.trimMargin()
            val tileArrangement = findCornerTiles(tiles)
            println(tileArrangement)
        }

        @Test
        internal fun `example 1, also rotate the templates`() {
            val tileArrangement = findCornerTiles(tiles = example1)

            assertThat(tileArrangement.tiles[Coordinate(0, 0)]!!.id, equalTo(1951))
            assertThat(tileArrangement.tiles[Coordinate(1, 0)]!!.id, equalTo(2311))
            assertThat(tileArrangement.tiles[Coordinate(2, 0)]!!.id, equalTo(3079))

            assertThat(tileArrangement.tiles[Coordinate(0, 1)]!!.id, equalTo(2729))
            assertThat(tileArrangement.tiles[Coordinate(1, 1)]!!.id, equalTo(1427))
            assertThat(tileArrangement.tiles[Coordinate(2, 1)]!!.id, equalTo(2473))

            assertThat(tileArrangement.tiles[Coordinate(0, 2)]!!.id, equalTo(2971))
            assertThat(tileArrangement.tiles[Coordinate(1, 2)]!!.id, equalTo(1489))
            assertThat(tileArrangement.tiles[Coordinate(2, 2)]!!.id, equalTo(1171))
        }

        @Test
        internal fun part1() {
            // takes about 2 minutes
//            val input = readFile("/input-day20.txt")
//            val tileArrangement = findCornerTiles(input)
//
//            println(tileArrangement)
//            assertThat(
//                tileArrangement.top.left().id * tileArrangement.top.right().id * tileArrangement.bottom.left().id * tileArrangement.bottom.right().id
//                , equalTo(79412832860579L)
//            )
//            val tiles = tileArrangement.withBorderlessTiles().toString()
//            File("src/main/resources/day20-pt1-output.txt").writeText(
//                tiles.lines()
//                    .filter { it.isNotEmpty() }
//                    .map { it.filter { c -> c != ' ' } }
//                    .joinToString("\n")
//            )
        }
    }

    @Nested
    inner class Part_2_find_the_seamonsters {

        @Test
        internal fun `this is a sea monster`() {
            val seaMonster = """
                ..................#.
                #....##....##....###
                .#..#..#..#..#..#...
            """.trimIndent()
        }

        @Test
        internal fun `if template is not present in the image then return an empty list`() {
            val template = """
                #
                #
            """.trimIndent()
            val image = """
                ..
                ..
            """.trimIndent()

            assertThat(
                image.findAll(template),
                equalTo(emptyList())
            )
        }

        @Test
        internal fun `finds the image template in the provided image`() {
            val template = """
                #.
                .#
            """.trimIndent()
            val image = """
                #..
                .#.
            """.trimIndent()

            assertThat(
                image.findAll(template),
                equalTo(listOf(Coordinate(0, 0), Coordinate(1, 1)))
            )
        }

        @Test
        internal fun `finds the flipped image template in the provided image`() {
            val template = """
                .#
                #.
            """.trimIndent()
            val image = """
                .#.
                #..
            """.trimIndent()

            assertThat(
                image.findAll(template),
                equalTo(listOf(Coordinate(1, 0), Coordinate(0, 1)))
            )
        }

        @Test
        internal fun `finds a different image template in the provided image`() {
            val template = """
                #
                #
            """.trimIndent()
            val image = """
                .#.
                .#.
            """.trimIndent()

            assertThat(
                image.findAll(template),
                equalTo(listOf(Coordinate(1, 0), Coordinate(1, 1)))
            )
        }

        @Test
        internal fun `finds yet another image template in the provided image`() {
            val template = """
                #.#
                .#.
            """.trimIndent()
            val image = """
                .#.#
                ..#.
            """.trimIndent()

            assertThat(
                image.findAll(template),
                equalTo(listOf(Coordinate(1, 0), Coordinate(3, 0), Coordinate(2, 1)))
            )
        }

        @Test
        internal fun `also finds the template after flipping the image`() {
            val template = """
                #.#
                .##
            """.trimIndent()
            val image = """
                ....
                ..##
                .#.#
                ....
            """.trimIndent()

            val actual = image.findAllInAllOrientations(template)
            val hit = actual.entries.first { it.value.isNotEmpty() }
            assertThat(
                hit.key,
                equalTo(
                    """
                       ....
                       .#.#
                       ..##
                       ....
                   """.trimIndent()
                )
            )
            assertThat(
                hit.value,
                equalTo(listOf(Coordinate(1, 1), Coordinate(3, 1), Coordinate(2, 2), Coordinate(3, 2)))
            )
        }

        @Test
        internal fun `example 1 but with image flipped and rotated correctly`() {
            val image = part2ExampleInputFlippedWithMonsters.replace('O', '#')
            val seaMonster = """
                ..................#.
                #....##....##....###
                .#..#..#..#..#..#...
            """.trimIndent()
            val seaMonsterLocations = image.findAll(seaMonster)
            assertThat(
                (image.toCoordinateMap() - seaMonsterLocations).size,
                equalTo(273)
            )

        }

        @Test
        internal fun `example, find the sea monster`() {
            val seaMonster = """
                ..................#.
                #....##....##....###
                .#..#..#..#..#..#...
            """.trimIndent()
            val actual = part2ExampleInput.findAllInAllOrientations(seaMonster)
            val hit = actual.entries.first { it.value.isNotEmpty() }
            assertThat(
                (hit.key.toCoordinateMap() - hit.value).size,
                equalTo(273)
            )
        }

        @Test
        internal fun `part 2`() {
            val seaMonster = """
                ..................#.
                #....##....##....###
                .#..#..#..#..#..#...
            """.trimIndent()
            val actual = part2Input.findAllInAllOrientations(seaMonster)
            val hit = actual.entries.first { it.value.isNotEmpty() }
            assertThat(
                (hit.key.toCoordinateMap() - hit.value).size,
                equalTo(2155)
            )
        }
    }

}

private val example1 = """
                |Tile 2311:
                |..##.#..#.
                |##..#.....
                |#...##..#.
                |####.#...#
                |##.##.###.
                |##...#.###
                |.#.#.#..##
                |..#....#..
                |###...#.#.
                |..###..###
                |
                |Tile 1951:
                |#.##...##.
                |#.####...#
                |.....#..##
                |#...######
                |.##.#....#
                |.###.#####
                |###.##.##.
                |.###....#.
                |..#.#..#.#
                |#...##.#..
                |
                |Tile 1171:
                |####...##.
                |#..##.#..#
                |##.#..#.#.
                |.###.####.
                |..###.####
                |.##....##.
                |.#...####.
                |#.##.####.
                |####..#...
                |.....##...
                |
                |Tile 1427:
                |###.##.#..
                |.#..#.##..
                |.#.##.#..#
                |#.#.#.##.#
                |....#...##
                |...##..##.
                |...#.#####
                |.#.####.#.
                |..#..###.#
                |..##.#..#.
                |
                |Tile 1489:
                |##.#.#....
                |..##...#..
                |.##..##...
                |..#...#...
                |#####...#.
                |#..#.#.#.#
                |...#.#.#..
                |##.#...##.
                |..##.##.##
                |###.##.#..
                |
                |Tile 2473:
                |#....####.
                |#..#.##...
                |#.##..#...
                |######.#.#
                |.#...#.#.#
                |.#########
                |.###.#..#.
                |########.#
                |##...##.#.
                |..###.#.#.
                |
                |Tile 2971:
                |..#.#....#
                |#...###...
                |#.#.###...
                |##.##..#..
                |.#####..##
                |.#..####.#
                |#..#.#..#.
                |..####.###
                |..#.#.###.
                |...#.#.#.#
                |
                |Tile 2729:
                |...#.#.#.#
                |####.#....
                |..#.#.....
                |....#..#.#
                |.##..##.#.
                |.#.####...
                |####.#.#..
                |##.####...
                |##..#.##..
                |#.##...##.
                |
                |Tile 3079:
                |#.#.#####.
                |.#..######
                |..#.......
                |######....
                |####.#..#.
                |.#...#.##.
                |#.#####.##
                |..#.###...
                |..#.......
                |..#.###...
            """.trimMargin()

val part2ExampleInput = """
    |.#.#..#.##...#.##..#####
    |###....#.#....#..#......
    |##.##.###.#.#..######...
    |###.#####...#.#####.#..#
    |##.#....#.##.####...#.##
    |...########.#....#####.#
    |....#..#...##..#.#.###..
    |.####...#..#.....#......
    |#..#.##..#..###.#.##....
    |#.####..#.####.#.#.###..
    |###.#.#...#.######.#..##
    |#.####....##..########.#
    |##..##.#...#...#.#.#.#..
    |...#..#..#.#.##..###.###
    |.#.#....#.##.#...###.##.
    |###.#...#..#.##.######..
    |.#.#.###.##.##.#..#.##..
    |.####.###.#...###.#..#.#
    |..#.#..#..#.#.#.####.###
    |#..####...#.#.#.###.###.
    |#####..#####...###....##
    |#.##..#..#...#..####...#
    |.#.###..##..##..####.##.
    |...###...##...#...#..###
""".trimMargin()

val part2ExampleInputFlippedWithMonsters = """
    .####...#####..#...###..
    #####..#..#.#.####..#.#.
    .#.#...#.###...#.##.O#..
    #.O.##.OO#.#.OO.##.OOO##
    ..#O.#O#.O##O..O.#O##.##
    ...#.#..##.##...#..#..##
    #.##.#..#.#..#..##.#.#..
    .###.##.....#...###.#...
    #.####.#.#....##.#..#.#.
    ##...#..#....#..#...####
    ..#.##...###..#.#####..#
    ....#.##.#.#####....#...
    ..##.##.###.....#.##..#.
    #...#...###..####....##.
    .#.##...#.##.#.#.###...#
    #.###.#..####...##..#...
    #.###...#.##...#.##O###.
    .O##.#OO.###OO##..OOO##.
    ..O#.O..O..O.#O##O##.###
    #.#..##.########..#..##.
    #.#####..#.#...##..#....
    #....##..#.#########..##
    #...#.....#..##...###.##
    #..###....##.#...##.##.#
""".trimIndent()

val part2Input = """
    |.#...#.###...#.....#....#..#.....###.#.##....##..####..##..........#....#...#..#......#.....#...
    |.#.#...#.#....#.#...........#........#...#...#..#..#.#..##.##....#..#.##........#.##..##.......#
    |...#...#............###....#...#..#....#....#......##.###..#.......#.....##.......##.#.........#
    |...#......#.#....#.....#....#..............#......##....#.....#.#......###.....##....#..#.##....
    |....#.....##........#..#........#....#.#.##..##....##.##...#....#..#......#.#......#..#..##.....
    |.....#....#...........#.....#........#.#..##.......##........#.......#.......#......#......#...#
    |.....#..#....#..............#..#####..#......#...##..#..#.....#.............##..##....###.......
    |.####.#...............#.#.##.#....#.......#.................#....#..#.............#.#....#......
    |.#.#.......#.#...#.....#.#......#..#........#..##..................#..##..#......#...#..#.....#.
    |..#.#..##..........#........##.##.#.#........#.##...##.#......#....#.........#..#..#....#.....##
    |...##...#.....#.#..#...###......###....#.#...#......................#..#.#..#.....#.#..#.#..#.##
    |......#.#..#....##....#..###..#.####......#....#.#.....#...............##.#...#..##...#..#...#..
    |......##...#..#.......#.......#..#.#.......###.#.....###...###..#..........##....#.#......####..
    |...#...#..#....#...#.##...##.#.##.......###..##....#.##..#.####.....#.#...#.#...#.##.....#...#.#
    |.##....#..#..#.....#.#.#...#.#.#..####.#.##.#...#....#.#......#.##.#...##...............##...##.
    |...#...#.#...#.##..#..#........#.......#....#.#...#.#.#.#..#...........#.#..#.....#.....##.#....
    |......#...#.......#.#..##..#....#..........#..##..#...#.#..#..##.....#.#..#..#...###.#...##.##.#
    |.#........##...#....#....#....#...#..##..#........#..#.#...#.......#....#........#........#.#...
    |.###.....##..#.#.###...#..#####...#.....###...##..#..#.#...#....#..#..#.....##...###....#.#..#..
    |..##........#....##....#...#....#...#.#.#..#.###..#...#.....#.#...#...#...........#.....##..#...
    |..##....#..#........#.......####...#.....#.##.#.#.#..#...##...#...#..#.##...###..#..##..#......#
    |...##...#.#.....#.#..###...###.#....#.............#....#..#...#...#.####...............##..##.#.
    |.#........#.#....#...##.....#...##..#............#.#..#.#..##..........#..##.#....#....##..#....
    |....#.##...##....#.##..#..##.#..#....#...#.#.........#...#.#....###..#..#......####..#..........
    |#.##..#.###.......##..#...##.....#.#....#.##.#....##.#...###......#...#..#.#....####.#.#..#..#..
    |#.#...#.........##..#...#.......#.#.#...##..####...#..##....#.....###..........#.##....#.....#..
    |.##.#......#.......##...#......##..#.....#....#..#...#.#.....#..........#.##.......#...........#
    |..##...#..#.#..#.#...#.#..###.##....#..#..#.......#............##......#..##....#....#......#...
    |..###.....#..#.###.............#....#......##.....##..#.#.###.#.....####.#...#....#...#.........
    |...........#..#.##.......#.#.......#.....#....##.#...#...#.##..#...##..###...#...###..#.#.......
    |...#.#.........#.#.###.#........#..#.#.###...#......##.....#####.####.#................#..##....
    |..#....#....#.##...##...#....#.....###.###...##..#...##.#...#....#......#..#..###..#..#.#.....#.
    |###......#.#..##..#.#.......#..####.....#.....#.#..##.###........##..##........#.....#.........#
    |..#.......#..#.#.#..##...#...........#...#.##.##.......#....#...#.........#......#.......#......
    |..#..#..###.....#......#..#.#...#...#.....#....#...##.......#.......##.#...#..##.....#...#.....#
    |#.........#.#...#.#.####..#........#......#..##..#.....#..##.#....#.#..#......#...#..#...#......
    |#...#.#...#..#.......#.#...#....####.......######...#.#...##.##.....#...##...#.....##......#....
    |.##.#...............##.#...#........#.#...#..##...........##..##.#..#...#...........#...#....#..
    |..#...##.........#..#....##.....##.##..#.....###.#.........#.#.............##.#....#.#..#.......
    |.#....###..#...#...#.#.##...#...##.......####....#.##..##..#.......#.#.#.#...#....#......#.###.#
    |##.......##.....#.....##..##....#...#..##.......#...#.........##.#..#..#....#...#...#...........
    |...##.#......#........#...#...##...#..#.......#....##............#....#..#....##.......#..#..#..
    |...#.....#.#...#####.#..#..#.#....####......####...#.....###.##.....#.#..#...#.#.###.....#......
    |...#..#.#.#........##..#..#.#....#.#.#...#..###..#..#.....#.......#...##..#.#...#.#..#....##....
    |##..##....##.#...#.##........##............#.#..##.#......##........##......#.#......#.#.##...#.
    |....###.........#...#.#.#...#.#.......##..#....#..##..#.....##.#..#......#...#..#..#............
    |....#.....###.........#.......#....#.####.#........##....#####..#...................##...#......
    |..##.....#.....#..#..#.......#.#.#...#....#.#.....#.#..........#.#..#.#..............#.....#..#.
    |.###...#.#....#.#.#.......#...#.....#.##....#.....#...##..#..#..##....#...##.#.###.......#.#..#.
    |....#.#...#.#.#...........##.....#..#......#.#......#............#..#.#...#.#...#....#.........#
    |...#.......#.#.#..#....###...#..#.....###..##.....###........###.#..#.....###..##.#.##..........
    |..#.#......#.........#...#......##...........#####.##...#.#.....#.#..........##.#...#.#...#.....
    |.#.......###....#...#..###..#.#....###...#...#......#........#.##........#.#........##...#.##.##
    |#..##....##.#.#...#..#...##..#..............#..............#.#.#.#.##.#...##.........#...#.#....
    |#..#####.#...#.....##........#...#.#........#...#.#....##...........#........#..#...#.#.....#.##
    |.#....#.#.#..#..#....#..#.#.....###....##..#.###....#.#...#.......#.#.#.....#...##............#.
    |.#......#..........#.#.###.##...##......#......#..##.........#..##....##...###...#...#.#.#....#.
    |....#..........#.#........#..#...#...#.##.......#.##......##....#..###.#.#..###...###........##.
    |.......##.#...#....#.#..##......#..#...#.##..#..#..#............#.......#..#.##..#..#..#....##..
    |......#.#####..#....#....#...#......#...#...#.####.#.#.###.#.#...#.##.#.#..#.#.......#..#...#...
    |..#.....##..#......##.....#.#....#.#.#......##.....#...##..........###......##.....##.#.#....##.
    |.###..#..#..##......#..#.....#..#.....#.#....#.#....#.#.#...##..#.#..##.............#.###.##....
    |#.#.#....#.....#.##...##.....#..#..#....#..#..#.#................#....#....#.........#..#...#..#
    |.....#.##.....#..#....##...#...#.###..##...#.#.###..#..##.......#..#..#...##..#...#.#....#...##.
    |...#..##.#....##.............#.........#.##..#..#.##...#.#.#..###.#..##.#.####.#...##...........
    |###..#...#.....#..........#....#........#...#........##..#......#..............##...#.#.#.......
    |..#.....#......#..#....#......##.#.........##....##........#.#...#.....#..#....##.....#....#.##.
    |.###...#...#...#..#...#...#..#..#..#..#.....##......#....#.##.#...........#.##.#.......#.#.....#
    |...........#...#...#..#........##..#....##..#....#..##..#...##.......#...#..........#..#.....#..
    |...#....#...#.##...#..##.##....###.....##.#..##.........#.....#.#......#...##.#.....#....##.###.
    |.#.#..........#.##..#.#.#...........#..#..............##.#......#.#.#..#.##.......#..#....####..
    |..#...##.......#....####...##.....#....##....#....#.#..........#...#.#.##.#......#.#...##.###...
    |..#.#.#...#.....#.#.#..#..#..#........#..#.........#..##..##.#...###........#....##.....#.......
    |#..#..#..#.##...#....##..#...#..........#.#..#........##.#..........#..#..#......#.......#.##.##
    |.#.............#......#...........#.....#........##...#.#..##...##....##......##..###...#..#.#..
    |...###......#.#.###....#........####..##..#..#.#.....#.##........#.#..#......................##.
    |#.###........###.......#.#.###.#..#...####.##............#...#....#...#.#......#.#....#.........
    |..#..#..####..#.....##.##.....#.....#.##.#...#..#..##.....#.#..###..........###.....#.#....###.#
    |.###......#..#....#.#.##..#.#.#...#.....#...#.......#..##..............#.......#.#.#......#.#..#
    |.####....#..#........##.....#.....##..#...#......#.......#..#..##..........#.........#.#....#...
    |##...#.#..#....#...#.##......#..........#.....#.#....#..#.#.##....#.#.......###.#.#...##.....##.
    |#....#..#..#..##.#....##......##..#.#....#....#....#...##..........#.......#......##..#...##..#.
    |....##...##.#..#......#...#.#.#...#...#..##...#..#......#.......##.....#....#.##.....######..#..
    |..#...#.#..........#..............#.....##........#..........#..#...#...#.......#...#.......##..
    |..#...#.#.#......#.....#.....##..#........#...##.......##.#..........#.#...........##.#.###.#...
    |....#...#....#.....#..#...#..#...#.......#.##..#...##....................#...#....#....#..#.##..
    |.......#....##.........#.....#...##....#......##........##.....#...#...#...#...#..##...#..#.##.#
    |#....#.......#......#.#.........#.#....#....#.....#.##.....##.....#.##...#...........#.#.....###
    |#...#..........###.#...#..#....#..#..#.....#.#....####......##.....##......#.....##.#.#.#....##.
    |#.....#....#..##.##.#..##....#.....#........#..##...#..#.#............#...##........#........##.
    |.....#.#.#.#.......#.##..#.#......#...#.#....#......#.#....#.....#......#.#......###.##.....#..#
    |...#......#.##...#.#.....#...#.#.....##.#.....#.##.#..#...#..#...#...#.#...###.##........#.##...
    |..##.##..##......#....#...#..#...#.#...#..#..##..#..........##.#...#....#..#..##.#...#......#...
    |...#...#..##...#.....#.#.#.###.....#..##.#.....#.#..#.......#........#.........##........##.....
    |##..#.#.#.......###.......###........#.#....#......#.#.#..#.....#........#.....#........##...#..
    |.....#.##.....###...#....##.##.#...#.#..#.....#..#....#....##....#..##.#..#..#.....#......#.....
""".trimMargin()
