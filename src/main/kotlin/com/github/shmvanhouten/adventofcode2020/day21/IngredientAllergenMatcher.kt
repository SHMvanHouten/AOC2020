package com.github.shmvanhouten.adventofcode2020.day21

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

fun countOccurrencesOfIngredientsWithoutAllergens(
    ingredientList: List<Pair<Set<Ingredient>, Set<Allergen>>>
): Int {
    val ingredientsWithoutAllergens = findIngredientsWithoutAllergens(ingredientList)
    return ingredientList.flatMap { it.first }.count { ingredientsWithoutAllergens.contains(it) }
}

fun findIngredientsWithoutAllergens(ingredientList: List<Pair<Set<Ingredient>, Set<Allergen>>>): Set<Ingredient> {
    return ingredientList.flatMap { it.first }.toSet() - matchIngredientsToAllergens(ingredientList)
        .flatMap { it.value }
}

fun matchIngredientsToAllergens(ingredientList: List<Pair<Set<Ingredient>, Set<Allergen>>>): Map<Allergen, Set<Ingredient>> {
    return ingredientList
        .flatMap { it.second }
        .distinct()
        .associateWith { findPossibleIngredientsForAllergens(it, ingredientList) }
}

fun findPossibleIngredientsForAllergens(
    allergen: Allergen,
    ingredientList: List<Pair<Set<Ingredient>, Set<Allergen>>>
): Set<Ingredient> {
    return ingredientList
        .filter { it.second.contains(allergen) }
        .map { it.first }
        .reduce {acc, ingredientsToCheck ->
            acc.filter { ingredientsToCheck.contains(it) }.toSet()
        }
}

fun String.parse(): List<Pair<Set<Ingredient>, Set<Allergen>>> {
    return this.lines()
        .map { splitIngredientsAndAllergens(it) }
}

fun splitIngredientsAndAllergens(rawLine: String): Pair<Set<Ingredient>, Set<Allergen>> {
    val (ingredients, allergens) = rawLine.splitIntoTwo(" (contains ")
    return ingredients.split(" ").toSet() to allergens.substring(0, allergens.length - 1).split(", ").toSet()
}

typealias Ingredient = String
typealias Allergen = String