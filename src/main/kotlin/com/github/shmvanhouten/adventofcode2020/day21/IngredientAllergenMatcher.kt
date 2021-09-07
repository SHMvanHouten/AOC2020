package com.github.shmvanhouten.adventofcode2020.day21

import com.github.shmvanhouten.adventofcode2017.util.splitIntoTwo

fun findDangerousIngredients(
    ingredientList: List<Pair<Set<Ingredient>, Set<Allergen>>>
): List<Pair<Ingredient, Allergen>> {
    val ingredientsByAllergens: MutableMap<Allergen, Set<Ingredient>> = matchIngredientsToAllergens(ingredientList).toMutableMap()
    val allergenicIngredients = mutableListOf<Pair<Ingredient, Allergen>>()

    while (ingredientsByAllergens.isNotEmpty()) {
        val (allergen, ingredientSet) = ingredientsByAllergens.poll()
        val ingredient = ingredientSet.first()
        allergenicIngredients.add(allergen to ingredient)
        ingredientsByAllergens.removeFromIngredients(ingredient)
    }

    return allergenicIngredients
        .sortedBy { it.first }
}

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

private fun MutableMap<Allergen, Set<Ingredient>>.poll(): Pair<Allergen, Set<Ingredient>> {
    val (allergen, ingredients) = this.entries.first { it.value.size == 1 }
    this.remove(allergen)
    return allergen to ingredients
}

private fun  MutableMap<Allergen, Set<Ingredient>>.removeFromIngredients(ingredient: Ingredient) {
    this.filter { it.value.contains(ingredient) }
        .forEach { (allergen, ingredients) ->
            this[allergen] = ingredients - ingredient
        }
}

typealias Ingredient = String
typealias Allergen = String
