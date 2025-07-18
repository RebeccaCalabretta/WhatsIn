package de.syntax_institut.androidabschlussprojekt.domain.usecase

import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.filter.StaticFilterValues
import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.BrandMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.CorporationMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.CountryMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.IngredientMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.NutriScoreMapper
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.utils.filter.FilterConfig
import de.syntax_institut.androidabschlussprojekt.utils.filter.prepareMappedItems
import kotlinx.coroutines.flow.StateFlow

class FilterConfigUseCase {

    operator fun invoke(
        active: ActiveFilter,
        searchText: StateFlow<String>,
        language: String,
        onUpdateFilter: (ActiveFilter) -> Unit
    ): List<FilterConfig> {

        val searchValue = searchText.value.lowercase()

        val (allergens, allergensToggle) = prepareMappedItems(
            rawItems = StaticFilterValues.allergens,
            selectedTags = active.excludedAllergens,
            searchValue = searchValue,
            mapper = { AllergenMapper.map(it, language) },
            reverseMapper = { AllergenMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(excludedAllergens = it)) }

        val (ingredients, ingredientsToggle) = prepareMappedItems(
            StaticFilterValues.ingredients,
            active.excludedIngredients,
            searchValue,
            { IngredientMapper.map(it, language) },
            { IngredientMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(excludedIngredients = it)) }

        val (additives, additivesToggle) = prepareMappedItems(
            StaticFilterValues.additives,
            active.excludedAdditives,
            searchValue,
            { AdditiveMapper.map(it, language) },
            { AdditiveMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(excludedAdditives = it)) }

        val (labels, labelsToggle) = prepareMappedItems(
            StaticFilterValues.labels,
            active.allowedLabels,
            searchValue,
            { LabelMapper.map(it, language) ?: "" },
            { LabelMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(allowedLabels = it)) }

        val (countries, countriesToggle) = prepareMappedItems(
            StaticFilterValues.countries,
            active.allowedCountry,
            searchValue,
            { CountryMapper.map(it, language) },
            { CountryMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(allowedCountry = it)) }

        val (brands, brandsToggle) = prepareMappedItems(
            StaticFilterValues.brands,
            active.excludedBrands,
            searchValue,
            { BrandMapper.map(it) },
            { BrandMapper.getReverseMap()[it] }
        ) { onUpdateFilter(active.copy(excludedBrands = it)) }

        val (nutri, nutriToggle) = prepareMappedItems(
            StaticFilterValues.nutriScore,
            active.allowedNutriScore.map { it.lowercase() },
            searchValue,
            { NutriScoreMapper.map(it) ?: it },
            { NutriScoreMapper.getReverseMap()[it.uppercase()] ?: it }
        ) { onUpdateFilter(active.copy(allowedNutriScore = it.map { score -> score.lowercase() })) }

        val (corporations, corpToggle) = prepareMappedItems(
            StaticFilterValues.corporations,
            active.excludedCorporations,
            searchValue,
            { CorporationMapper.map(it) ?: it },
            { CorporationMapper.getReverseMap()[it] }
        ) { onUpdateFilter(active.copy(excludedCorporations = it)) }

        return listOf(
            FilterConfig(
                R.string.exclude_ingredients,
                ingredients,
                active.excludedIngredients.map { IngredientMapper.map(it, language) },
                ingredientsToggle
            ),
            FilterConfig(
                R.string.exclude_allergens,
                allergens,
                active.excludedAllergens.map { AllergenMapper.map(it, language) },
                allergensToggle
            ),
            FilterConfig(
                R.string.exclude_additives,
                additives,
                active.excludedAdditives.map { AdditiveMapper.map(it, language) },
                additivesToggle
            ),
            FilterConfig(
                R.string.choose_labels,
                labels,
                active.allowedLabels.mapNotNull { LabelMapper.map(it, language) },
                labelsToggle
            ),
            FilterConfig(
                R.string.available_in,
                countries,
                active.allowedCountry.map { CountryMapper.map(it, language) },
                countriesToggle
            ),
            FilterConfig(
                R.string.exclude_brands,
                brands,
                active.excludedBrands.map { BrandMapper.map(it) },
                brandsToggle
            ),
            FilterConfig(
                R.string.choose_nutriscore,
                nutri,
                active.allowedNutriScore.map { it.lowercase() },
                nutriToggle
            ),
            FilterConfig(
                R.string.exclude_corporations,
                corporations,
                active.excludedCorporations.mapNotNull { CorporationMapper.map(it) },
                corpToggle
            )
        )
    }
}