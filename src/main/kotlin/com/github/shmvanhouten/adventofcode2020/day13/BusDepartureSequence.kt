package com.github.shmvanhouten.adventofcode2020.day13

fun findEarliestTimestampWhereBusesComeInAtThePrescribedTimesBruteForce(busIDsByOffset: List<Bus>): Long {
//    val bus0 = busIDsByOffset[0]?: 1
    val bus0 = 1
    busIDsByOffset.map { it.offset + it.id }
    return generateSequence(bus0.toLong()) {it + bus0}
        .first { i ->
            println()
            println(i/bus0)
            busIDsByOffset.map {
                print((i + it.offset) % it.id)
                print(", ")
                (i + it.offset) % it.id
            }.all { it == 0L }
        }
}

//fun findEarliestTimestampWhereBusesComeInAtThePrescribedTimesBruteForce(busIDsByOffset: List<Pair<Int, Int>>): Long {
//    val bus0 = busIDsByOffset.maxBy { it.second }!!
//
//    busIDsByOffset.map { it.first + it.second }
//    return generateSequence(bus0.second.toLong()) {it + bus0.second.toLong()}
//        .first { i ->
////            println()
////            println(i/bus0)
//            busIDsByOffset.map {
////                print((i + it.first) % it.second)
////                print(", ")
//                (i + (it.first - bus0.first)) % it.second
//            }.all { it == 0L }
//        } - bus0.first
//}

fun findEarliestTimestampWhereBusesComeInAtThePrescribedTimes(buses: List<Bus>): Long {
    var iterationSize = 1L
    var startPoint = 1L
    for (bus in buses) {
        val results = generateSequence(startPoint) { it + iterationSize }
            .filter { i ->
                (i + bus.offset) % bus.id == 0L
            }.take(2).toList()
        startPoint = results[0]
        iterationSize = results[1] - startPoint
        println("startPoint: $startPoint")
        println("iterationSize: $iterationSize")

    }

    return startPoint

}