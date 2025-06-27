package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.filter.StaticFilterValues
import de.syntax_institut.androidabschlussprojekt.data.mapping.IngredientMapper
import de.syntax_institut.androidabschlussprojekt.data.mapping.LabelMapper
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.utils.filter.FilterConfig
import de.syntax_institut.androidabschlussprojekt.utils.filter.loadFilterFromDataStore
import de.syntax_institut.androidabschlussprojekt.utils.filter.prepareFilterItems
import de.syntax_institut.androidabschlussprojekt.utils.filter.saveFilterToDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class FilterViewModel(
    private val context: Context
) : ViewModel() {

    private val _activeFilter = MutableStateFlow(ActiveFilter())
    val activeFilter: StateFlow<ActiveFilter> = _activeFilter

    private val _filterViolations = MutableStateFlow<List<String>>(emptyList())
    val filterViolations: StateFlow<List<String>> = _filterViolations

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    init {
        Log.d("FilterViewModel", "FilterViewModel gestartet – Filter wird vorbereitet")
        viewModelScope.launch {
            _activeFilter.value = loadFilterFromDataStore(context, json)
            _isLoading.value = false
        }
    }

    fun updateFilter(newFilter: ActiveFilter) {
        _activeFilter.value = newFilter
        viewModelScope.launch {
            saveFilterToDataStore(context, json, newFilter)
        }
    }

    fun buildFilterConfigs(): List<FilterConfig> {
        val active = activeFilter.value

        val (ingredients, ingredientsToggle) = prepareFilterItems(
            StaticFilterValues.ingredients,
            active.excludedIngredients,
            { updateFilter(active.copy(excludedIngredients = it)) },
            map = { IngredientMapper.map(it) ?: it }        )

        val (allergens, allergensToggle) = prepareFilterItems(
            StaticFilterValues.allergens,
            active.excludedAllergens,
            { updateFilter(active.copy(excludedAllergens = it)) },
            allLabel = "Alle"
        )

        val (additives, additivesToggle) = prepareFilterItems(
            StaticFilterValues.additives,
            active.excludedAdditives,
            { updateFilter(active.copy(excludedAdditives = it)) },
            allLabel = "Alle"
        )

        val (labels, labelsToggle) = prepareFilterItems(
            StaticFilterValues.labels,
            active.allowedLabels,
            { updateFilter(active.copy(allowedLabels = it)) },
            map = { LabelMapper.map(it) ?: it }        )

        val (countries, countriesToggle) = prepareFilterItems(
            StaticFilterValues.countries,
            active.allowedCountry,
            { updateFilter(active.copy(allowedCountry = it)) }
        )

        val (brands, brandsToggle) = prepareFilterItems(
            StaticFilterValues.brands,
            active.excludedBrands,
            { updateFilter(active.copy(excludedBrands = it)) }
        )

        val (nutri, nutriToggle) = prepareFilterItems(
            StaticFilterValues.nutriScore,
            active.allowedNutriScore,
            { updateFilter(active.copy(allowedNutriScore = it)) }
        )

        val (corporations, corpToggle) = prepareFilterItems(
            StaticFilterValues.corporations,
            active.excludedCorporations,
            { updateFilter(active.copy(excludedCorporations = it)) }
        )

        return listOf(
            FilterConfig("Exclude Ingredients", ingredients, active.excludedIngredients, ingredientsToggle),
            FilterConfig("Exclude Allergens", allergens, active.excludedAllergens, allergensToggle),
            FilterConfig("Exclude Additives", additives, active.excludedAdditives, additivesToggle),
            FilterConfig("Select Labels", labels, active.allowedLabels, labelsToggle),
            FilterConfig("Available in", countries, active.allowedCountry, countriesToggle),
            FilterConfig("Exclude Brands", brands, active.excludedBrands, brandsToggle),
            FilterConfig("Nutri-Score", nutri, active.allowedNutriScore, nutriToggle),
            FilterConfig("Exclude Corporations", corporations, active.excludedCorporations, corpToggle)
        )
    }

    fun checkFilter(product: Product, filter: ActiveFilter): List<String> {
        val violations = mutableListOf<String>()

        val matchedAllergens = product.allergensTags.intersect(filter.excludedAllergens.toSet())
        if (matchedAllergens.isNotEmpty()) {
            violations.add("Enthält ausgeschlossene Allergene: ${matchedAllergens.joinToString()}")
        }

        val matchedAdditives = product.additivesTags.intersect(filter.excludedAdditives.toSet())
        if (matchedAdditives.isNotEmpty()) {
            violations.add("Enthält ausgeschlossene Zusatzstoffe: ${matchedAdditives.joinToString()}")
        }

        val matchedIngredients = product.ingredientsTags.intersect(filter.excludedIngredients.toSet())
        if (matchedIngredients.isNotEmpty()) {
            violations.add("Enthält ausgeschlossene Zutaten: ${matchedIngredients.joinToString()}")
        }

        if (!product.brand.isNullOrBlank() && product.brand in filter.excludedBrands) {
            violations.add("Marke ist ausgeschlossen: ${product.brand}")
        }

        if (!product.corporation.isNullOrBlank() && product.corporation in filter.excludedCorporations) {
            violations.add("Konzern ist ausgeschlossen: ${product.corporation}")
        }

        if (filter.allowedLabels.isNotEmpty()) {
            val matchedLabels = product.labelsTags.intersect(filter.allowedLabels.toSet())
            if (matchedLabels.isEmpty()) {
                violations.add("Erforderliche Label fehlen")
            }
        }

        if (filter.allowedNutriScore.isNotEmpty()) {
            val score = product.nutriScore?.trim()?.uppercase()
            if (score !in filter.allowedNutriScore.map { it.uppercase() }) {
                violations.add("Nutri-Score nicht erlaubt: ${score ?: "Unbekannt"}")
            }
        }

        if (filter.allowedCountry.isNotEmpty()) {
            val matchedCountries = product.countriesTags.intersect(filter.allowedCountry.toSet())
            if (matchedCountries.isEmpty()) {
                violations.add("Nicht verfügbar in den ausgewählten Ländern")
            }
        }

        return violations
    }

    fun validateProduct(product: Product) {
        val filter = _activeFilter.value
        _filterViolations.value = checkFilter(product, filter)
    }
}
