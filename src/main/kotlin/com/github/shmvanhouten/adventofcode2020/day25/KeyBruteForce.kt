package com.github.shmvanhouten.adventofcode2020.day25

private const val DIVISOR = 20201227
fun bruteForceLoopNumber(subjectNr: Int, publicKey: Int): Int {
    var loopNumber = 0
    var value = 1
    while (value != publicKey) {
        value *= subjectNr
        value %= DIVISOR
        loopNumber += 1
    }
    return loopNumber
}

fun bruteForcePrivateKey(subjectNr: Int, publicKey1: Int, publicKey2: Int): Long {
    val loopSize1 = bruteForceLoopNumber(subjectNr, publicKey1)
    val loopSize2 = bruteForceLoopNumber(subjectNr, publicKey2)

    val privateKey1 = calculatePrivateKey(publicKey1, loopSize2)
    val privateKey2 = calculatePrivateKey(publicKey2, loopSize1)
    if (privateKey1 != privateKey2) error("private keys $privateKey1, $privateKey2 don't match!")
    return privateKey1
}

private fun calculatePrivateKey(subjectNr: Int, loopNumber: Int): Long {
    var value = 1L
    repeat(loopNumber) {
        value *= subjectNr
        value %= DIVISOR
    }
    return value
}