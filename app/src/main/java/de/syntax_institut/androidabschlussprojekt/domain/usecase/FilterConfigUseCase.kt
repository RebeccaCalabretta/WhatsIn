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

        val searchValue = searchText.value

        val filteredIngredients = StaticFilterValues.ingredients.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (ingredients, ingredientsToggle) = prepareMappedItems(
            raw = filteredIngredients,
            selected = active.excludedIngredients,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedIngredients = updatedItems)) },
            mapper = { tag -> IngredientMapper.map(tag, language) }
        )

        val filteredAllergens = StaticFilterValues.allergens.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (allergens, allergensToggle) = prepareMappedItems(
            raw = filteredAllergens,
            selected = active.excludedAllergens,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedAllergens = updatedItems)) },
            mapper = { tag -> AllergenMapper.map(tag, language) }
        )

        val filteredAdditives = StaticFilterValues.additives.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (additives, additivesToggle) = prepareMappedItems(
            raw = filteredAdditives,
            selected = active.excludedAdditives,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedAdditives = updatedItems)) },
            mapper = { tag -> AdditiveMapper.map(tag, language) }
        )

        val filteredLabels = StaticFilterValues.labels.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (labels, labelsToggle) = prepareMappedItems(
            raw = filteredLabels,
            selected = active.allowedLabels,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedLabels = updatedItems)) },
            mapper = { tag -> LabelMapper.map(tag, language) }
        )

        val filteredCountries = StaticFilterValues.countries.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (countries, countriesToggle) = prepareMappedItems(
            raw = filteredCountries,
            selected = active.allowedCountry,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedCountry = updatedItems)) },
            mapper = { tag -> CountryMapper.map(tag, language) }
        )

        val filteredBrands = StaticFilterValues.brands.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (brands, brandsToggle) = prepareMappedItems(
            raw = filteredBrands,
            selected = active.excludedBrands,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedBrands = updatedItems)) },
            mapper = BrandMapper::map
        )

        val filteredNutriScore = StaticFilterValues.nutriScore.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (nutri, nutriToggle) = prepareMappedItems(
            raw = filteredNutriScore,
            selected = active.allowedNutriScore,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedNutriScore = updatedItems)) },
            mapper = NutriScoreMapper::map
        )

        val filteredCorporations = StaticFilterValues.corporations.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (corporations, corpToggle) = prepareMappedItems(
            raw = filteredCorporations,
            selected = active.excludedCorporations,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedCorporations = updatedItems)) },
            mapper = CorporationMapper::map
        )

        return listOf(
            FilterConfig(
                titleRes = R.string.exclude_ingredients,
                items = ingredients,
                selectedItems = active.excludedIngredients,
                onToggleItem = ingredientsToggle
            ),
            FilterConfig(
                titleRes = R.string.exclude_allergens,
                items = allergens,
                selectedItems = active.excludedAllergens,
                onToggleItem = allergensToggle
            ),
            FilterConfig(
                titleRes = R.string.exclude_additives,
                items = additives,
                selectedItems = active.excludedAdditives,
                onToggleItem = additivesToggle
            ),
            FilterConfig(
                titleRes = R.string.choose_labels,
                items = labels,
                selectedItems = active.allowedLabels,
                onToggleItem = labelsToggle
            ),
            FilterConfig(
                titleRes = R.string.available_in,
                items = countries,
                selectedItems = active.allowedCountry,
                onToggleItem = countriesToggle
            ),
            FilterConfig(
                titleRes = R.string.exclude_brands,
                items = brands,
                selectedItems = active.excludedBrands,
                onToggleItem = brandsToggle
            ),
            FilterConfig(
                titleRes = R.string.choose_nutriscore,
                items = nutri,
                selectedItems = active.allowedNutriScore,
                onToggleItem = nutriToggle
            ),
            FilterConfig(
                titleRes = R.string.exclude_corporations,
                items = corporations,
                selectedItems = active.excludedCorporations,
                onToggleItem = corpToggle
            )
        )
    }
}