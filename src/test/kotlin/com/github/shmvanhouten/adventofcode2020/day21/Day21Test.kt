package com.github.shmvanhouten.adventofcode2020.day21

import com.github.shmvanhouten.adventofcode2020.util.FileReader.readFile
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day21Test {

    // Each allergen is found in exactly one ingredient
    // Each ingredient contains one or no allergens
    // Allergens aren't always marked
    // An allergen could not be listed even if it is found in an ingredient, but each allergen will be found somewhere
    // in the ingredients list

    @Nested
    inner class Part1 {

        @Test
        internal fun `fish could be mxmxvkd or sqjhc`() {
            val ingredientsByAllergens = matchIngredientsToAllergens(example1.parse())

            val allergens = ingredientsByAllergens["fish"]
            assertThat(allergens?.size, equalTo(2))
            assertThat(allergens, equalTo(setOf("sqjhc", "mxmxvkd")))
        }

        @Test
        internal fun `dairy could be mxmxvkd only`() {
            val ingredientsByAllergens = matchIngredientsToAllergens(example1.parse())

            val allergens = ingredientsByAllergens["dairy"]
            assertThat(allergens?.size, equalTo(1))
            assertThat(allergens, equalTo(setOf("mxmxvkd")))
        }

        @Test
        internal fun `soy can be sqjhc or fvjkl`() {
            val ingredientsByAllergens = matchIngredientsToAllergens(example1.parse())

            val allergens = ingredientsByAllergens["soy"]
            assertThat(allergens?.size, equalTo(2))
            assertThat(allergens, equalTo(setOf("sqjhc", "fvjkl")))
        }

        @Test
        internal fun `which means that kfcds, nhms, sbzzf, or trh do not contain an allergen`() {
            val ingredientList = example1.parse()
            val ingredientsWithoutAllergens = findIngredientsWithoutAllergens(ingredientList)
            assertThat(
                ingredientsWithoutAllergens,
                equalTo(setOf("kfcds", "nhms", "sbzzf", "trh"))
            )
            assertThat(
                countOccurrencesOfIngredientsWithoutAllergens(example1.parse()),
                equalTo(5)
            )
        }

        @Test
        internal fun `part 1`() {
            assertThat(
                countOccurrencesOfIngredientsWithoutAllergens(readFile("/input-day21.txt").parse()),
                equalTo(2436)
            )
        }
    }

    @Nested
    inner class Part_2 {

        @Test
        internal fun `which means that mxmxvkd contains dairy`() {

        }
    }

    val example1 = """
        mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
        trh fvjkl sbzzf mxmxvkd (contains dairy)
        sqjhc fvjkl (contains soy)
        sqjhc mxmxvkd sbzzf (contains fish)
    """.trimIndent()

}

