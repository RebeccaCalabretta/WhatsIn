package de.syntax_institut.androidabschlussprojekt.domain.usecase

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.CountryMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.helper.FilterType
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.model.FilterViolation
import de.syntax_institut.androidabschlussprojekt.model.Product

class FilterCheckUseCase {
    operator fun invoke(
        product: Product,
        filter: ActiveFilter,
        selectedLanguage: String
    ): List<FilterViolation> {
        val violations = mutableListOf<FilterViolation>()

        product.ingredientsTags
            .intersect(filter.excludedIngredients.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let {
                violations.add(
                    FilterViolation(
                        R.string.violation_ingredients,
                        it.joinToString(),
                        FilterType.INGREDIENTS
                    )
                )
            }

        product.allergensTags
            .intersect(filter.excludedAllergens.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let {
                violations.add(
                    FilterViolation(
                        R.string.violation_allergens,
                        it.joinToString(),
                        FilterType.ALLERGENS
                    )
                )
            }

        product.additivesTags
            .intersect(filter.excludedAdditives.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let {
                violations.add(
                    FilterViolation(
                        R.string.violation_additives,
                        it.joinToString(),
                        FilterType.ADDITIVES
                    )
                )
            }

        product.brand?.takeIf { it in filter.excludedBrands }
            ?.let {
                violations.add(
                    FilterViolation(
                        R.string.violation_brands,
                        it,
                        FilterType.BRANDS
                    )
                )
            }

        product.corporation?.takeIf { it in filter.excludedCorporations }
            ?.let {
                violations.add(
                    FilterViolation(
                        R.string.violation_corporations,
                        it,
                        FilterType.CORPORATIONS
                    )
                )
            }

        if (filter.allowedLabels.isNotEmpty()) {
            // Log die Ausgangswerte
            Log.d("FilterCheck", "product.labelsTags: ${product.labelsTags}")
            Log.d("FilterCheck", "filter.allowedLabels: ${filter.allowedLabels}")

            // Robust: Nimm NUR Mapperwerte, ignoriere null/""/unbekannt
            val matchedLabels = product.labelsTags
                .mapNotNull { LabelMapper.map(it, selectedLanguage) }
            val requiredLabels = filter.allowedLabels
                .mapNotNull { LabelMapper.map(it, selectedLanguage) }
            val missing = requiredLabels.filter { it.isNotBlank() && it !in matchedLabels }

            // Log das Ergebnis nach dem Mapping
            Log.d("FilterCheck", "matchedLabels: $matchedLabels")
            Log.d("FilterCheck", "requiredLabels: $requiredLabels")
            Log.d("FilterCheck", "missing: $missing")

            if (missing.isNotEmpty()) {
                violations.add(
                    FilterViolation(
                        R.string.violation_labels,
                        missing.joinToString(),
                        FilterType.LABELS
                    )
                )
            }
        }

        if (filter.allowedNutriScore.isNotEmpty()) {
            val score = product.nutriScore?.uppercase()
            val allowedScores = filter.allowedNutriScore.map { it.uppercase() }
            if (score !in allowedScores) {
                violations.add(
                    FilterViolation(
                        R.string.violation_nutriscore,
                        score.orEmpty(),
                        FilterType.NUTRISCORE
                    )
                )
            }
        }

        if (filter.allowedCountry.isNotEmpty()) {
            val matchedCountries = product.countriesTags.map { CountryMapper.map(it, selectedLanguage) }
            val requiredCountries = filter.allowedCountry.map { CountryMapper.map(it, selectedLanguage) }
            val common = matchedCountries.intersect(requiredCountries.toSet())
            if (common.isEmpty()) {
                violations.add(
                    FilterViolation(
                        R.string.violation_countries,
                        requiredCountries.joinToString(),
                        FilterType.COUNTRIES
                    )
                )
            }
        }

        return violations
    }
}