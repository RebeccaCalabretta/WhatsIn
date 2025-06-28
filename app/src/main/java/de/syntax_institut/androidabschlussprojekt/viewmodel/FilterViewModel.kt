package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.filter.StaticFilterValues
import de.syntax_institut.androidabschlussprojekt.data.mapping.CountryMapper
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
            FilterConfig("Zutatenen ausschließen", ingredients, active.excludedIngredients, ingredientsToggle),
            FilterConfig("Allergene ausschließen", allergens, active.excludedAllergens, allergensToggle),
            FilterConfig("Zusatzstoffe ausschließen", additives, active.excludedAdditives, additivesToggle),
            FilterConfig("Labels wählen", labels, active.allowedLabels, labelsToggle),
            FilterConfig("Erhältlich in", countries, active.allowedCountry, countriesToggle),
            FilterConfig("Marken ausschließen", brands, active.excludedBrands, brandsToggle),
            FilterConfig("Nutri-Score wählen", nutri, active.allowedNutriScore, nutriToggle),
            FilterConfig("Konzerne ausschließen", corporations, active.excludedCorporations, corpToggle)
        )
    }

    fun checkFilter(product: Product, filter: ActiveFilter): List<String> {
        val violations = mutableListOf<String>()

        product.ingredientsTags
            .intersect(filter.excludedIngredients.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let { violations.add("Beinhaltet ausgeschlossene Zutaten: ${it.joinToString()}") }

        product.allergensTags
            .intersect(filter.excludedAllergens.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let { violations.add("Beinhaltet ausgeschlossene Allergene: ${it.joinToString()}") }

        product.additivesTags
            .intersect(filter.excludedAdditives.toSet())
            .takeIf { it.isNotEmpty() }
            ?.let { violations.add("Beinhaltet ausgeschlossene Zusatzstoffe: ${it.joinToString()}") }

        product.brand?.takeIf { it in filter.excludedBrands }
            ?.let { violations.add("Beinhaltet ausgeschlossene Marken: $it") }

        product.corporation?.takeIf { it in filter.excludedCorporations }
            ?.let { violations.add("Beinhaltet ausgeschlossene Konzerne: $it") }

        if (filter.allowedLabels.isNotEmpty()) {
            val matched = product.labelsTags.mapNotNull { LabelMapper.map(it) }
            val required = filter.allowedLabels
            val missing = required.filter { it !in matched }
            if (missing.isNotEmpty()) {
                violations.add("Erforderliche Labels fehlen: ${missing.joinToString()}")
            }
        }

        if (filter.allowedNutriScore.isNotEmpty()) {
            val score = product.nutriScore?.uppercase()
            if (score !in filter.allowedNutriScore.map { it.uppercase() }) {
                violations.add("Nicht erlaubter Nutri-Score: $score")
            }
        }

        if (filter.allowedCountry.isNotEmpty()) {
            val matched = product.countriesTags.mapNotNull { CountryMapper.map(it) }
            val required = filter.allowedCountry
            val common = matched.intersect(required.toSet())
            if (common.isEmpty()) {
                violations.add("Nicht verfügbar in: ${required.joinToString()}")
            }
        }

        return violations
    }

    fun validateProduct(product: Product) {
        val filter = _activeFilter.value
        _filterViolations.value = checkFilter(product, filter)
    }
}
