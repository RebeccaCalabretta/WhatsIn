package de.syntax_institut.androidabschlussprojekt.domain.usecase

import de.syntax_institut.androidabschlussprojekt.data.filter.StaticFilterValues
import de.syntax_institut.androidabschlussprojekt.data.mapping.IngredientMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.utils.filter.FilterConfig
import de.syntax_institut.androidabschlussprojekt.utils.filter.prepareFilterItems
import kotlinx.coroutines.flow.StateFlow

class FilterConfigUseCase {
    operator fun invoke(
        active: ActiveFilter,
        searchText: StateFlow<String>,
        onUpdateFilter: (ActiveFilter) -> Unit
    ): List<FilterConfig> {

        val (ingredients, ingredientsToggle) = prepareFilterItems(
            StaticFilterValues.ingredients,
            active.excludedIngredients,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedIngredients = updatedItems)) },
            map = { IngredientMapper.map(it) ?: it }
        )

        val (allergens, allergensToggle) = prepareFilterItems(
            StaticFilterValues.allergens,
            active.excludedAllergens,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedAllergens = updatedItems)) },
            allLabel = "Alle"
        )

        val (additives, additivesToggle) = prepareFilterItems(
            StaticFilterValues.additives,
            active.excludedAdditives,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedAdditives = updatedItems)) },
            allLabel = "Alle"
        )

        val (labels, labelsToggle) = prepareFilterItems(
            StaticFilterValues.labels,
            active.allowedLabels,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedLabels = updatedItems)) },
            map = { LabelMapper.map(it) ?: it }
        )

        val (countries, countriesToggle) = prepareFilterItems(
            StaticFilterValues.countries,
            active.allowedCountry,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedCountry = updatedItems)) }
        )

        val (brands, brandsToggle) = prepareFilterItems(
            StaticFilterValues.brands,
            active.excludedBrands,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedBrands = updatedItems)) }
        )

        val (nutri, nutriToggle) = prepareFilterItems(
            StaticFilterValues.nutriScore,
            active.allowedNutriScore,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedNutriScore = updatedItems)) }
        )

        val (corporations, corpToggle) = prepareFilterItems(
            StaticFilterValues.corporations,
            active.excludedCorporations,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedCorporations = updatedItems)) },
            allLabel = "Alle",

            )

        return listOf(
            FilterConfig("Zutaten ausschließen", ingredients, active.excludedIngredients, ingredientsToggle),
            FilterConfig("Allergene ausschließen", allergens, active.excludedAllergens, allergensToggle),
            FilterConfig("Zusatzstoffe ausschließen", additives, active.excludedAdditives, additivesToggle),
            FilterConfig("Labels wählen", labels, active.allowedLabels, labelsToggle),
            FilterConfig("Erhältlich in", countries, active.allowedCountry, countriesToggle),
            FilterConfig("Marken ausschließen", brands, active.excludedBrands, brandsToggle),
            FilterConfig("Nutri-Score wählen", nutri, active.allowedNutriScore, nutriToggle),
            FilterConfig("Konzerne ausschließen", corporations, active.excludedCorporations, corpToggle)
        )
    }
}