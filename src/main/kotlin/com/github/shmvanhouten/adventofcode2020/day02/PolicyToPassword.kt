package com.github.shmvanhouten.adventofcode2020.day02

data class PolicyToPassword(val policy: Policy, val password: Password)

data class Policy(val min: Int, val max: Int, val char:Char)

typealias Password = String

fun toPolicyAndPassword(raw: String): PolicyToPassword {
    val split = raw.split(':')
    return PolicyToPassword(
        toPolicy(
            split[0]
        ), split[1].trim()
    )
}

fun toPolicy(raw: String) : Policy {
    val split = raw.split(' ')
    val char = split[1][0]

    val minMax = split[0].split('-').map { it.toInt() }

    return Policy(minMax[0], minMax[1], char)
}