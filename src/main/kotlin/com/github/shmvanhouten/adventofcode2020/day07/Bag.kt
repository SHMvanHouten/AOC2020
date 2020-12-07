package com.github.shmvanhouten.adventofcode2020.day07

data class Bag(val colorCoding: ColorCode, val mustContain: Map<ColorCode, Int>)

fun parseBags(input: String): List<Bag> {
    return input.lines()
        .map { toBag(it) }
}

fun toBag(rawBag: String): Bag {
    val colorCoding = rawBag.substring(0, rawBag.indexOf(" bags"))
    val contents = toContents(rawBag)
    return Bag(colorCoding, contents)
}

private fun toContents(rawBag: String): Map<String, Int> {
    return if (rawBag.contains("contain no other bags.")) {
        emptyMap()
    } else if (rawBag.contains(',')) {
        rawBag.substring(rawBag.indexOf("contain ") + "contain ".length, rawBag.length - 1)
            .split(", ")
            .map { toContentPair(it) }
            .toMap()
    } else {
        mapOf(toContentPair(rawBag.substring(rawBag.indexOf("contain ") + "contain ".length)))
    }
}

private fun toContentPair(it: String) = it.substring(it.indexOf(' ') + 1, it.indexOf(" bag")) to it.substring(0, it.indexOf(' ')).toInt()

typealias ColorCode = String