package de.syntax_institut.androidabschlussprojekt.domain.usecase

import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.data.filter.StaticFilterValues
import de.syntax_institut.androidabschlussprojekt.data.mapping.AdditiveMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.AllergenMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.BrandMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.CountryMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.IngredientMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.NutriScoreMapper
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.utils.filter.FilterConfig
import de.syntax_institut.androidabschlussprojekt.utils.filter.prepareFilterItems
import de.syntax_institut.androidabschlussprojekt.utils.filter.prepareMappedItems

class FilterConfigUseCase {

    operator fun invoke(
        active: ActiveFilter,
        searchText: String,
        language: String,
        allFiltersChip: String,
        onUpdateFilter: (ActiveFilter) -> Unit,
    ): List<FilterConfig> {

        val searchValue = searchText.lowercase()

        val (allergens, allergensToggle) = prepareMappedItems(
            rawItems = StaticFilterValues.allergens,
            selectedTags = active.excludedAllergens,
            searchValue = searchValue,
            mapper = { AllergenMapper.map(it, language) },
            reverseMapper = { AllergenMapper.getReverseMap(language)[it] },
            onUpdate = { onUpdateFilter(active.copy(excludedAllergens = it)) },
            sortSelectedFirst = true
        )

        val (ingredients, ingredientsToggle) = prepareMappedItems(
            rawItems = StaticFilterValues.ingredients,
            selectedTags = active.excludedIngredients,
            searchValue = searchValue,
            mapper = { IngredientMapper.map(it, language) },
            reverseMapper = { IngredientMapper.getReverseMap(language)[it] },
            onUpdate = { onUpdateFilter(active.copy(excludedIngredients = it)) },
            sortSelectedFirst = true
        )

        val (additives, additivesToggle) = prepareFilterItems(
            rawItems = StaticFilterValues.additives,
            selected = active.excludedAdditives,
            update = { onUpdateFilter(active.copy(excludedAdditives = it)) },
            allLabel = allFiltersChip,
            searchValue = searchValue,
            map = { AdditiveMapper.map(it, language) }
        )

        val (labels, labelsToggle) = prepareMappedItems(
            rawItems = StaticFilterValues.labels,
            selectedTags = active.allowedLabels,
            searchValue = searchValue,
            mapper = { LabelMapper.map(it, language) ?: "" },
            reverseMapper = { LabelMapper.getReverseMap(language)[it] },
            onUpdate = { onUpdateFilter(active.copy(allowedLabels = it)) },
            sortSelectedFirst = true
        )

        val (countries, countriesToggle) = prepareMappedItems(
            rawItems = StaticFilterValues.countries,
            selectedTags = active.allowedCountry,
            searchValue = searchValue,
            mapper = { CountryMapper.map(it, language) },
            reverseMapper = { CountryMapper.getReverseMap(language)[it] },
            onUpdate = { onUpdateFilter(active.copy(allowedCountry = it)) },
            sortSelectedFirst = true
        )

        val (brands, brandsToggle) = prepareMappedItems(
            rawItems = StaticFilterValues.brands,
            selectedTags = active.excludedBrands,
            searchValue = searchValue,
            mapper = { BrandMapper.map(it) },
            reverseMapper = { BrandMapper.getReverseMap()[it] },
            onUpdate = { onUpdateFilter(active.copy(excludedBrands = it)) },
            sortSelectedFirst = true
        )

        val (nutri, nutriToggle) = prepareMappedItems(
            rawItems = StaticFilterValues.nutriScore,
            selectedTags = active.allowedNutriScore.map { it.lowercase() },
            searchValue = searchValue,
            mapper = { NutriScoreMapper.map(it) ?: it },
            reverseMapper = { NutriScoreMapper.getReverseMap()[it.uppercase()] ?: it },
            onUpdate = { onUpdateFilter(active.copy(allowedNutriScore = it.map { score -> score.lowercase() })) },
            sortSelectedFirst = false
        )

        val (corporations, corpToggle) = prepareFilterItems(
            rawItems = StaticFilterValues.corporations,
            selected = active.excludedCorporations,
            update = { onUpdateFilter(active.copy(excludedCorporations = it)) },
            allLabel = allFiltersChip,
            searchValue = searchValue,
            map = { it }
        )

        return listOf(
            FilterConfig(
                titleRes = R.string.exclude_ingredients,
                items = ingredients,
                selectedItems = active.excludedIngredients.map { IngredientMapper.map(it, language) },
                onToggleItem = ingredientsToggle,
                sortSelectedFirst = true
            ),
            FilterConfig(
                titleRes = R.string.exclude_allergens,
                items = allergens,
                selectedItems = active.excludedAllergens.map { AllergenMapper.map(it, language) },
                onToggleItem = allergensToggle,
                sortSelectedFirst = true
            ),
            FilterConfig(
                titleRes = R.string.exclude_additives,
                items = additives,
                selectedItems = active.excludedAdditives.map { AdditiveMapper.map(it, language) },
                onToggleItem = additivesToggle,
                sortSelectedFirst = true
            ),
            FilterConfig(
                titleRes = R.string.choose_labels,
                items = labels,
                selectedItems = active.allowedLabels.mapNotNull { LabelMapper.map(it, language) },
                onToggleItem = labelsToggle,
                sortSelectedFirst = true
            ),
            FilterConfig(
                titleRes = R.string.available_in,
                items = countries,
                selectedItems = active.allowedCountry.map { CountryMapper.map(it, language) },
                onToggleItem = countriesToggle,
                sortSelectedFirst = true
            ),
            FilterConfig(
                titleRes = R.string.exclude_brands,
                items = brands,
                selectedItems = active.excludedBrands.map { BrandMapper.map(it) },
                onToggleItem = brandsToggle,
                sortSelectedFirst = true
            ),
            FilterConfig(
                titleRes = R.string.choose_nutriscore,
                items = nutri,
                selectedItems = active.allowedNutriScore.map { it.lowercase() },
                onToggleItem = nutriToggle,
                sortSelectedFirst = false
            ),
            FilterConfig(
                titleRes = R.string.exclude_corporations,
                items = corporations,
                selectedItems = active.excludedCorporations +
                        (if (active.excludedCorporations.size == StaticFilterValues.corporations.size) listOf(allFiltersChip) else emptyList()),
                onToggleItem = corpToggle,
                sortSelectedFirst = true
            )
        )
    }
}