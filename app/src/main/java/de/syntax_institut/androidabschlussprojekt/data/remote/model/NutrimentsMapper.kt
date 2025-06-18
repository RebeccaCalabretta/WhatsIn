package de.syntax_institut.androidabschlussprojekt.data.remote.model

import de.syntax_institut.androidabschlussprojekt.model.Nutriments

fun NutrimentsDto.toNutriments(): Nutriments {
    return Nutriments(
        energyKcal = energyKcalPer100g,
        fat = fatPer100g,
        saturatedFat = saturatedFatPer100g,
        carbohydrates = carbsPer100g,
        fiber = fiberPer100g,
        proteins = proteinsPer100g,
        salt = saltPer100g,
        sugars = sugarsPer100g
    )
}