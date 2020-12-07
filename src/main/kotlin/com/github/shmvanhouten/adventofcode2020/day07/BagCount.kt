package com.github.shmvanhouten.adventofcode2020.day07

fun countBagsThatCanContain(allBags: List<Bag>, target: String): Long {
    var bagsThatContain = allBags
        .filter { it.mustContain.containsKey(target) }.toSet()
    val bags = (allBags - bagsThatContain).filter { it.colorCoding != target }.toMutableSet()
    val bagsToCheck = bagsThatContain.toMutableSet()

    while (bagsToCheck.isNotEmpty()) {
        val bag = bagsToCheck.first()
        bagsToCheck.remove(bag)

        val newBagsToCheck = bags
            .filter { it.mustContain.containsKey(bag.colorCoding) }
            .filter { !bagsThatContain.contains(it) }
        bagsToCheck.addAll(newBagsToCheck)
        bagsThatContain += newBagsToCheck
    }


    return bagsThatContain.size.toLong()
}


fun countAmountOfBagsItHolds(input: List<Bag>, target: String): Long {
    return countAmountOfBagsItHolds(input, input.first { it.colorCoding == target })
}

fun countAmountOfBagsItHolds(allBags: List<Bag>, target: Bag): Long {
    var bagCount = 0L
    val bagsToMultiplier = target.mustContain
        .map { (code, amount) -> allBags.first { it.colorCoding == code } to amount.toLong() }
        .toMap().toMutableMap()

    while (bagsToMultiplier.isNotEmpty()) {
        val (bag, multiplier) = bagsToMultiplier.entries.first()
        bagsToMultiplier.remove(bag)

        bagCount += multiplier

        val bagsInside = bag.mustContain
            .map { (code, amount) -> allBags.first { it.colorCoding == code } to amount * multiplier }

        bagsInside.forEach { insideBag ->
            bagsToMultiplier.merge(insideBag.first, insideBag.second) {a, b -> a + b}
        }

    }

    return bagCount
}

