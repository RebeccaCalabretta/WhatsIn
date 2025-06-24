package de.syntax_institut.androidabschlussprojekt.model

import kotlinx.serialization.Serializable

@Serializable
data class ActiveFilter(
    val allowedLabels: List<String> = emptyList(),
    val allowedNutriScore: List<String> = emptyList(),
    val allowedCountry: List<String> = emptyList(),
    val excludedAllergens: List<String> = emptyList(),
    val excludedAdditives: List<String> = emptyList(),
    val excludedIngredients: List<String> = emptyList(),
    val excludedBrands: List<String> = emptyList(),
    val excludedCorporations: List<String> = emptyList(),
    )
