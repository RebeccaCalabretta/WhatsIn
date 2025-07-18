package de.syntax_institut.androidabschlussprojekt.utils

import android.content.Context
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Nutriments

fun Nutriments.formatNutriments(context: Context): List<String> {
    return listOf(
        context.getString(R.string.nutrient_energy, energyKcal?.toString() ?: "-"),
        context.getString(R.string.nutrient_fat, fat?.toString() ?: "-"),
        context.getString(R.string.nutrient_saturated_fat, saturatedFat?.toString() ?: "-"),
        context.getString(R.string.nutrient_carbs, carbohydrates?.toString() ?: "-"),
        context.getString(R.string.nutrient_fiber, fiber?.toString() ?: "-"),
        context.getString(R.string.nutrient_proteins, proteins?.toString() ?: "-"),
        context.getString(R.string.nutrient_salt, salt?.toString() ?: "-"),
        context.getString(R.string.nutrient_sugar, sugars?.toString() ?: "-")
    )
}

val Nutriments.hasAnyValue: Boolean
    get() = energyKcal != null || fat != null || saturatedFat != null ||
            carbohydrates != null || fiber != null || proteins != null ||
            salt != null || sugars != null