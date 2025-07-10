package de.syntax_institut.androidabschlussprojekt.domain.usecase

import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.mapping.CountryMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.model.FilterViolation
import de.syntax_institut.androidabschlussprojekt.model.Product

class FilterCheckUseCase {
    operator fun invoke(product: Product, filter: ActiveFilter): List<FilterViolation> {
        val violations = mutableListOf<FilterViolation>()

        product.ingredientsTags
            .intersect(filter.excludedIngredients.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let {
                violations.add(FilterViolation(R.string.violation_ingredients, it.joinToString()))
            }

        product.allergensTags
            .intersect(filter.excludedAllergens.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let {
                violations.add(FilterViolation(R.string.violation_allergens, it.joinToString()))
            }

        product.additivesTags
            .intersect(filter.excludedAdditives.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let {
                violations.add(FilterViolation(R.string.violation_additives, it.joinToString()))
            }

        product.brand?.takeIf { it in filter.excludedBrands }
            ?.let {
                violations.add(FilterViolation(R.string.violation_brands, it))
            }

        product.corporation?.takeIf { it in filter.excludedCorporations }
            ?.let {
                violations.add(FilterViolation(R.string.violation_corporations, it))
            }

        if (filter.allowedLabels.isNotEmpty()) {
            val matched = product.labelsTags.mapNotNull { LabelMapper.map(it) }
            val required = filter.allowedLabels
            val missing = required.filter { it !in matched }
            if (missing.isNotEmpty()) {
                violations.add(FilterViolation(R.string.violation_labels, missing.joinToString()))
            }
        }

        if (filter.allowedNutriScore.isNotEmpty()) {
            val score = product.nutriScore?.uppercase()
            if (score !in filter.allowedNutriScore.map { it.uppercase() }) {
                violations.add(FilterViolation(R.string.violation_nutriscore, score.orEmpty()))
            }
        }

        if (filter.allowedCountry.isNotEmpty()) {
            val matched = product.countriesTags.mapNotNull { CountryMapper.map(it) }
            val required = filter.allowedCountry
            val common = matched.intersect(required.toSet())
            if (common.isEmpty()) {
                violations.add(FilterViolation(R.string.violation_countries, required.joinToString()))
            }
        }

        return violations
    }
}