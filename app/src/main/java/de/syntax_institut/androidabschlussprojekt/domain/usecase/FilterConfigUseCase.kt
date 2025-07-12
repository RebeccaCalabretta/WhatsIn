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
import kotlinx.coroutines.flow.StateFlow

class FilterConfigUseCase {

    operator fun invoke(
        active: ActiveFilter,
        searchText: StateFlow<String>,
        language: String,
        onUpdateFilter: (ActiveFilter) -> Unit
    ): List<FilterConfig> {

        val searchValue = searchText.value.lowercase()

        fun prepare(
            rawItems: List<String>,
            selectedTags: List<String>,
            mapper: (String) -> String,
            reverseMapper: (String) -> String?,
            onUpdate: (List<String>) -> Unit
        ): Pair<List<String>, (String) -> Unit> {

            val itemsMapped = rawItems.map { tag -> mapper(tag) to tag }

            val filteredSortedItems = itemsMapped
                .filter { (label, _) -> label.lowercase().contains(searchValue) }
                .sortedWith(
                    compareByDescending<Pair<String, String>> { it.second in selectedTags }
                        .thenBy { it.first }
                )
                .map { it.first }

            val toggle: (String) -> Unit = { clickedLabel ->
                val clickedTag = reverseMapper(clickedLabel)
                if (clickedTag != null) {
                    val updated = if (clickedTag in selectedTags) {
                        selectedTags - clickedTag
                    } else {
                        selectedTags + clickedTag
                    }
                    onUpdate(updated)
                }
            }

            return Pair(filteredSortedItems, toggle)
        }

        val (allergens, allergensToggle) = prepare(
            rawItems = StaticFilterValues.allergens,
            selectedTags = active.excludedAllergens,
            mapper = { AllergenMapper.map(it, language) },
            reverseMapper = { AllergenMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(excludedAllergens = it)) }

        val (ingredients, ingredientsToggle) = prepare(
            StaticFilterValues.ingredients,
            active.excludedIngredients,
            { IngredientMapper.map(it, language) },
            { IngredientMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(excludedIngredients = it)) }

        val (additives, additivesToggle) = prepare(
            StaticFilterValues.additives,
            active.excludedAdditives,
            { AdditiveMapper.map(it, language) },
            { AdditiveMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(excludedAdditives = it)) }

        val (labels, labelsToggle) = prepare(
            StaticFilterValues.labels,
            active.allowedLabels,
            { LabelMapper.map(it, language) ?: it },
            { LabelMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(allowedLabels = it)) }

        val (countries, countriesToggle) = prepare(
            StaticFilterValues.countries,
            active.allowedCountry,
            { CountryMapper.map(it, language) },
            { CountryMapper.getReverseMap(language)[it] }
        ) { onUpdateFilter(active.copy(allowedCountry = it)) }

        val (brands, brandsToggle) = prepare(
            StaticFilterValues.brands,
            active.excludedBrands,
            { BrandMapper.map(it) },
            { BrandMapper.getReverseMap()[it] }
        ) { onUpdateFilter(active.copy(excludedBrands = it)) }

        val (nutri, nutriToggle) = prepare(
            StaticFilterValues.nutriScore,
            active.allowedNutriScore,
            { NutriScoreMapper.map(it) },
            { NutriScoreMapper.getReverseMap()[it] }
        ) { onUpdateFilter(active.copy(allowedNutriScore = it)) }

        val (corporations, corpToggle) = prepare(
            StaticFilterValues.corporations,
            active.excludedCorporations,
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
                active.allowedNutriScore.map { NutriScoreMapper.map(it) },
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