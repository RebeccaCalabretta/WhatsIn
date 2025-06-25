package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.filter.StaticFilterValues
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.helper.FilterType
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.utils.filter.FilterConfig
import de.syntax_institut.androidabschlussprojekt.utils.filter.loadFilterFromDataStore
import de.syntax_institut.androidabschlussprojekt.utils.filter.prepareGenericItems
import de.syntax_institut.androidabschlussprojekt.utils.filter.prepareItemsWithAll
import de.syntax_institut.androidabschlussprojekt.utils.filter.saveFilterToDataStore
import de.syntax_institut.androidabschlussprojekt.utils.filters.loadAvailableFiltersFromApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class FilterViewModel(
    appContext: Context,
    private val filterRepository: FilterRepository
) : ViewModel() {

    private val context = appContext.applicationContext

    private val _activeFilter = MutableStateFlow(ActiveFilter())
    val activeFilter: StateFlow<ActiveFilter> = _activeFilter

    private val _availableFilters = MutableStateFlow<Map<FilterType, List<FilterItem>>>(emptyMap())
    val availableFilters: StateFlow<Map<FilterType, List<FilterItem>>> = _availableFilters

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
            _availableFilters.value = loadAvailableFiltersFromApi(filterRepository)
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
        val available = availableFilters.value

        val (ingredientsItems, ingredientsToggle) = prepareGenericItems(
            StaticFilterValues.ingredients,
            active.excludedIngredients
        ) { updateFilter(active.copy(excludedIngredients = it)) }

        val (allergensItems, allergensToggle) = prepareItemsWithAll(
            available[FilterType.ALLERGENS]?.mapNotNull { it.name } ?: emptyList(),
            active.excludedAllergens,
            "All Allergens"
        ) { updateFilter(active.copy(excludedAllergens = it)) }

        val (additivesItems, additivesToggle) = prepareItemsWithAll(
            available[FilterType.ADDITIVES]?.mapNotNull { it.name } ?: emptyList(),
            active.excludedAdditives,
            "All Additives"
        ) { updateFilter(active.copy(excludedAdditives = it)) }

        val (labelsItems, labelsToggle) = prepareGenericItems(
            StaticFilterValues.labels,
            active.allowedLabels
        ) { updateFilter(active.copy(allowedLabels = it)) }

        val (countriesItems, countriesToggle) = prepareGenericItems(
            available[FilterType.COUNTRIES]?.mapNotNull { it.name } ?: emptyList(),
            active.allowedCountry
        ) { updateFilter(active.copy(allowedCountry = it)) }

        val (brandsItems, brandsToggle) = prepareGenericItems(
            available[FilterType.BRANDS]?.mapNotNull { it.name } ?: emptyList(),
            active.excludedBrands
        ) { updateFilter(active.copy(excludedBrands = it)) }

        val (nutriScoreItems, nutriScoreToggle) = prepareGenericItems(
            StaticFilterValues.nutriScore,
            active.allowedNutriScore
        ) { updateFilter(active.copy(allowedNutriScore = it)) }

        val (corporationsItems, corporationsToggle) = prepareGenericItems(
            StaticFilterValues.corporations,
            active.excludedCorporations
        ) { updateFilter(active.copy(excludedCorporations = it)) }

        return listOf(
            FilterConfig(
                "Exclude Ingredients",
                ingredientsItems,
                active.excludedIngredients,
                ingredientsToggle
            ),
            FilterConfig(
                "Exclude Allergens",
                allergensItems,
                active.excludedAllergens,
                allergensToggle
            ),
            FilterConfig(
                "Exclude Additives",
                additivesItems,
                active.excludedAdditives,
                additivesToggle
            ),
            FilterConfig("Select Labels", labelsItems, active.allowedLabels, labelsToggle),
            FilterConfig("Available in", countriesItems, active.allowedCountry, countriesToggle),
            FilterConfig("Exclude Brands", brandsItems, active.excludedBrands, brandsToggle),
            FilterConfig(
                "Nutri-Score",
                nutriScoreItems,
                active.allowedNutriScore,
                nutriScoreToggle
            ),
            FilterConfig(
                "Exclude Corporations",
                corporationsItems,
                active.excludedCorporations,
                corporationsToggle
            )
        )
    }
}
