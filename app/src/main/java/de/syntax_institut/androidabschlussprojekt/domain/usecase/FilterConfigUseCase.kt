package de.syntax_institut.androidabschlussprojekt.domain.usecase

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
            mapper = IngredientMapper::map
        )

        val filteredAllergens = StaticFilterValues.allergens.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (allergens, allergensToggle) = prepareMappedItems(
            raw = filteredAllergens,
            selected = active.excludedAllergens,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedAllergens = updatedItems)) },
            allLabel = "Alle",
            mapper = AllergenMapper::map
        )

        val filteredAdditives = StaticFilterValues.additives.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (additives, additivesToggle) = prepareMappedItems(
            raw = filteredAdditives,
            selected = active.excludedAdditives,
            update = { updatedItems -> onUpdateFilter(active.copy(excludedAdditives = updatedItems)) },
            allLabel = "Alle",
            mapper = AdditiveMapper::map
        )

        val fiteredLabels = StaticFilterValues.labels.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (labels, labelsToggle) = prepareMappedItems(
            raw = fiteredLabels,
            selected = active.allowedLabels,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedLabels = updatedItems)) },
            mapper = LabelMapper::map
        )

        val filteredCountries = StaticFilterValues.countries.filter {
            it.contains(searchValue, ignoreCase = true)
        }
        val (countries, countriesToggle) = prepareMappedItems(
            raw = filteredCountries,
            selected = active.allowedCountry,
            update = { updatedItems -> onUpdateFilter(active.copy(allowedCountry = updatedItems)) },
            mapper = CountryMapper::map
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
            allLabel = "Alle",
            mapper = CorporationMapper::map
        )

        return listOf(
            FilterConfig(
                "Zutaten ausschließen",
                ingredients,
                active.excludedIngredients,
                ingredientsToggle
            ),
            FilterConfig(
                "Allergene ausschließen",
                allergens,
                active.excludedAllergens,
                allergensToggle
            ),
            FilterConfig(
                "Zusatzstoffe ausschließen",
                additives,
                active.excludedAdditives,
                additivesToggle
            ),
            FilterConfig("Labels wählen", labels, active.allowedLabels, labelsToggle),
            FilterConfig("Erhältlich in", countries, active.allowedCountry, countriesToggle),
            FilterConfig("Marken ausschließen", brands, active.excludedBrands, brandsToggle),
            FilterConfig("Nutri-Score wählen", nutri, active.allowedNutriScore, nutriToggle),
            FilterConfig(
                "Konzerne ausschließen",
                corporations,
                active.excludedCorporations,
                corpToggle
            )
        )
    }
}