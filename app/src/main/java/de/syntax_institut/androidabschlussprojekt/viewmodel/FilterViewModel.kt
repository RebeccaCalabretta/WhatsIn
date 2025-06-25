package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.filter.StaticFilterValues
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.helper.FilterType
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.ui.components.filter.FilterConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class FilterViewModel(
    context: Context,
    private val filterRepository: FilterRepository
) : ViewModel() {

    private val dataStore = context.dataStore

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

    companion object {
        private val ACTIVE_FILTER_KEY = stringPreferencesKey("active_filter")
    }

    init {
        Log.d("FilterViewModel", "FilterViewModel gestartet – Filter wird vorbereitet")
        viewModelScope.launch {
            loadFilterFromDataStore()
            loadAvailableFilters()
        }
    }

    private suspend fun loadAvailableFilters() {
        _isLoading.value = true
        try {
            val allergens = filterRepository.fetchAllergens()
            Log.d("FilterViewModel", "fetchAllergens() erfolgreich")

            val additives = filterRepository.fetchAdditives()
            Log.d("FilterViewModel", "fetchAdditives() erfolgreich")

            val countries = filterRepository.fetchCountries()
            Log.d("FilterViewModel", "fetchCountries() erfolgreich")

            val brands = filterRepository.fetchBrands()
            Log.d("FilterViewModel", "fetchBrands() erfolgreich")

            _availableFilters.value = mapOf(
                FilterType.ALLERGENS to allergens,
                FilterType.ADDITIVES to additives,
                FilterType.COUNTRIES to countries,
                FilterType.BRANDS to brands
            )

            availableFilters.value.forEach { (type, items) ->
                Log.d("FilterViewModel", "Loaded $type items: ${items.take(3)}")
            }

        } catch (e: Exception) {
            Log.e("FilterViewModel", "Fehler beim Laden der Filterwerte", e)
        } finally {
            _isLoading.value = false
        }
    }

    private suspend fun loadFilterFromDataStore() {
        try {
            val prefs = dataStore.data.first()
            val jsonString = prefs[ACTIVE_FILTER_KEY]
            val filter = if (jsonString != null) {
                json.decodeFromString<ActiveFilter>(jsonString)
            } else {
                ActiveFilter()
            }
            _activeFilter.value = filter
            Log.d("FilterViewModel", "Filter erfolgreich geladen: $filter")
        } catch (e: Exception) {
            Log.e("FilterViewModel", "Fehler beim Laden des Filters", e)
        }
    }

    fun saveFilterToDataStore(newFilter: ActiveFilter) {
        viewModelScope.launch {
            try {
                val jsonString = json.encodeToString(newFilter)
                dataStore.edit { prefs ->
                    prefs[ACTIVE_FILTER_KEY] = jsonString
                }
                _activeFilter.value = newFilter
                Log.d("FilterViewModel", "Filter erfolgreich gespeichert: $newFilter")
            } catch (e: Exception) {
                Log.e("FilterViewModel", "Fehler beim Speichern des Filters", e)
            }
        }
    }

    fun updateFilter(newFilter: ActiveFilter) {
        _activeFilter.value = newFilter
        saveFilterToDataStore(newFilter)
    }

    fun buildFilterConfigs(): List<FilterConfig> {
        val active = activeFilter.value
        val available = availableFilters.value

        fun prepareItemsWithAll(
            rawItems: List<String>,
            selected: List<String>,
            allLabel: String
        ): Pair<List<String>, (String) -> Unit> {
            val cleaned = rawItems
                .filter { it.isNotBlank() && it.firstOrNull()?.isUpperCase() == true }
                .distinct()

            val selectedSet = selected.toSet()
            val (selectedItems, unselectedItems) = cleaned.partition { it in selectedSet }

            val sorted = buildList {
                add(allLabel)
                addAll(selectedItems.sorted())
                addAll(unselectedItems.sorted())
            }

            val toggle: (String) -> Unit = { item ->
                val current = selected.toMutableList()
                when {
                    item == allLabel && allLabel !in current -> {
                        current.clear()
                        current.add(allLabel)
                        current.addAll(cleaned)
                    }
                    item == allLabel -> {
                        current.clear()
                    }
                    item in current -> {
                        current.remove(item)
                        current.remove(allLabel)
                    }
                    else -> {
                        current.add(item)
                        current.remove(allLabel)
                    }
                }
                updateFilter(
                    when (allLabel) {
                        "All Allergens" -> active.copy(excludedAllergens = current)
                        "All Additives" -> active.copy(excludedAdditives = current)
                        else -> active
                    }
                )
            }

            return sorted to toggle
        }

        fun prepareGenericItems(
            rawItems: List<String>,
            selected: List<String>,
            onUpdate: (List<String>) -> Unit
        ): Pair<List<String>, (String) -> Unit> {
            val cleaned = rawItems
                .filter { it.isNotBlank() && it.firstOrNull()?.isUpperCase() == true }
                .distinct()

            val (selectedItems, unselectedItems) = cleaned.partition { it in selected }

            val sorted = selectedItems.sorted() + unselectedItems.sorted()

            val toggle: (String) -> Unit = { item ->
                val updated = if (item in selected) selected - item else selected + item
                onUpdate(updated)
            }

            return sorted to toggle
        }

        val (ingredientsItems, ingredientsToggle) = prepareGenericItems(
            StaticFilterValues.ingredients,
            active.excludedIngredients
        ) { updateFilter(active.copy(excludedIngredients = it)) }

        val (allergensItems, allergensToggle) = prepareItemsWithAll(
            available[FilterType.ALLERGENS]?.mapNotNull { it.name } ?: emptyList(),
            active.excludedAllergens,
            "All Allergens"
        )

        val (additivesItems, additivesToggle) = prepareItemsWithAll(
            available[FilterType.ADDITIVES]?.mapNotNull { it.name } ?: emptyList(),
            active.excludedAdditives,
            "All Additives"
        )

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
            FilterConfig("Avoid Ingredients", ingredientsItems, active.excludedIngredients, ingredientsToggle),
            FilterConfig("Avoid Allergens", allergensItems, active.excludedAllergens, allergensToggle),
            FilterConfig("Avoid Additives", additivesItems, active.excludedAdditives, additivesToggle),
            FilterConfig("Select Labels", labelsItems, active.allowedLabels, labelsToggle),
            FilterConfig("Available in", countriesItems, active.allowedCountry, countriesToggle),
            FilterConfig("Avoid Brands", brandsItems, active.excludedBrands, brandsToggle),
            FilterConfig("Nutri-Score", nutriScoreItems, active.allowedNutriScore, nutriScoreToggle),
            FilterConfig("Avoid Corporations", corporationsItems, active.excludedCorporations, corporationsToggle)
        )
    }
}
