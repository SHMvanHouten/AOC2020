package com.github.shmvanhouten.adventofcode2020.day04

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo
import java.util.regex.Pattern

fun countValidPassports(input: String): Int {
    return input.split("\n\n")
        .map { toPassport(it) }
        .count { it.isValid() }
}

fun toPassport(raw: String) : Passport {
    return Passport(raw.split(regex = Pattern.compile("[\n ]"))
        .map { it.splitIntoTwo(":") }
        .toMap())
}
