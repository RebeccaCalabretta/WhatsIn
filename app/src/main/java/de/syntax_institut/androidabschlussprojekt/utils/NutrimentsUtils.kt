package de.syntax_institut.androidabschlussprojekt.utils

import android.content.Context
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import java.text.NumberFormat
import java.util.Locale

fun Nutriments.formatNutrientPairs(context: Context): List<Pair<String, String>> {
    val locale = context.resources.configuration.locales[0]

    return listOf(
        context.getString(R.string.label_energy) to formatValueWithUnit(energyKcal, "kcal", locale),
        context.getString(R.string.label_fat) to formatValueWithUnit(fat, "g", locale),
        context.getString(R.string.label_saturated_fat) to formatValueWithUnit(saturatedFat, "g", locale),
        context.getString(R.string.label_carbs) to formatValueWithUnit(carbohydrates, "g", locale),
        context.getString(R.string.label_fiber) to formatValueWithUnit(fiber, "g", locale),
        context.getString(R.string.label_proteins) to formatValueWithUnit(proteins, "g", locale),
        context.getString(R.string.label_salt) to formatValueWithUnit(salt, "g", locale),
        context.getString(R.string.label_sugar) to formatValueWithUnit(sugars, "g", locale)
    )
}

val Nutriments.hasAnyValue: Boolean
    get() = energyKcal != null || fat != null || saturatedFat != null ||
            carbohydrates != null || fiber != null || proteins != null ||
            salt != null || sugars != null



fun formatValueWithUnit(
    value: Double?,
    unit: String,
    locale: Locale = Locale.getDefault()
): String {
    return value?.let {
        val formatter = NumberFormat.getNumberInstance(locale)
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 0
        "${formatter.format(it)} $unit"
    } ?: "-"
}