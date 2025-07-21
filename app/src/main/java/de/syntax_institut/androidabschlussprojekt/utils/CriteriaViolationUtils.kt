package de.syntax_institut.androidabschlussprojekt.utils

import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.BrandMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.CorporationMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.CountryMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.IngredientMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.NutriScoreMapper
import de.syntax_institut.androidabschlussprojekt.helper.FilterType

fun mapViolationValue(type: FilterType, value: String?, language: String): String? {
    return value
        ?.split(",")
        ?.joinToString(", ") {
            when (type) {
                FilterType.LABELS -> LabelMapper.map(it.trim(), language) ?: it.trim()
                FilterType.COUNTRIES -> CountryMapper.map(it.trim(), language)
                FilterType.ALLERGENS -> AllergenMapper.map(it.trim(), language)
                FilterType.INGREDIENTS -> IngredientMapper.map(it.trim(), language)
                FilterType.ADDITIVES -> AdditiveMapper.map(it.trim(), language)
                FilterType.BRANDS -> BrandMapper.map(it.trim()) ?: it.trim()
                FilterType.NUTRISCORE -> NutriScoreMapper.map(it.trim())?.uppercase() ?: "?"
                FilterType.CORPORATIONS -> CorporationMapper.map(it.trim()) ?: it.trim()
            }
        }
}