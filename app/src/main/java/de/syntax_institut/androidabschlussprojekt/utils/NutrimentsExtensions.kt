package de.syntax_institut.androidabschlussprojekt.utils

import de.syntax_institut.androidabschlussprojekt.model.Nutriments

fun Nutriments.formatNutriments(): String {
    return buildString {
        appendLine("Energie: ${energyKcal ?: "-"} kcal")
        appendLine("Fett: ${fat ?: "-"} g")
        appendLine("Gesättigte Fette: ${saturatedFat ?: "-"} g")
        appendLine("Kohlenhydrate: ${carbohydrates ?: "-"} g")
        appendLine("Ballaststoffe: ${fiber ?: "-"} g")
        appendLine("Proteine: ${proteins ?: "-"} g")
        appendLine("Salz: ${salt ?: "-"} g")
        appendLine("Zucker: ${sugars ?: "-"} g")
    }
}