package de.syntax_institut.androidabschlussprojekt.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Nutriments

@Composable
fun Nutriments.formatNutriments(): List<String> {
    return listOf(
        stringResource(R.string.nutrient_energy, energyKcal?.toString() ?: "-"),
        stringResource(R.string.nutrient_fat, fat?.toString() ?: "-"),
        stringResource(R.string.nutrient_saturated_fat, saturatedFat?.toString() ?: "-"),
        stringResource(R.string.nutrient_carbs, carbohydrates?.toString() ?: "-"),
        stringResource(R.string.nutrient_fiber, fiber?.toString() ?: "-"),
        stringResource(R.string.nutrient_proteins, proteins?.toString() ?: "-"),
        stringResource(R.string.nutrient_salt, salt?.toString() ?: "-"),
        stringResource(R.string.nutrient_sugar, sugars?.toString() ?: "-")
    )
}

val Nutriments.hasAnyValue: Boolean
    get() = energyKcal != null || fat != null || saturatedFat != null ||
            carbohydrates != null || fiber != null || proteins != null ||
            salt != null || sugars != null