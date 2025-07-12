package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterCheckUseCase
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterConfigUseCase
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.model.FilterViolation
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.utils.filter.FilterConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilterViewModel(
    private val filterRepository: FilterRepository,
    private val filterCheckUseCase: FilterCheckUseCase,
    private val filterConfigUseCase: FilterConfigUseCase
) : ViewModel() {

    private val _activeFilter = MutableStateFlow(ActiveFilter())
    val activeFilter: StateFlow<ActiveFilter> = _activeFilter

    private val _filterViolations = MutableStateFlow<List<FilterViolation>>(emptyList())
    val filterViolations: StateFlow<List<FilterViolation>> = _filterViolations

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    init {
        Log.d("FilterDebug", "FilterViewModel init – Filter wird vorbereitet")
        viewModelScope.launch {
            _activeFilter.value = filterRepository.getActiveFilter()
            _isLoading.value = false
            Log.d("FilterDebug", "Initial activeFilter: ${_activeFilter.value}")
        }
    }

    fun updateFilter(newFilter: ActiveFilter) {
        Log.d("FilterDebug", "updateFilter called. New filter: $newFilter")
        _activeFilter.value = newFilter
        viewModelScope.launch {
            filterRepository.saveActiveFilter(newFilter)
            Log.d("FilterDebug", "Filter saved to repository: $newFilter")
        }
    }

    fun buildFilterConfigs(searchText: String, selectedLanguage: String): List<FilterConfig> {
        val active = activeFilter.value
        Log.d("FilterDebug", "buildFilterConfigs called with searchText='$searchText', language='$selectedLanguage', activeFilter=$active")
        val result = filterConfigUseCase(active, this.searchText, selectedLanguage) { updatedFilter ->
            Log.d("FilterDebug", "buildFilterConfigs onUpdateFilter: $updatedFilter")
            updateFilter(updatedFilter)
        }
        Log.d("FilterDebug", "buildFilterConfigs returns FilterConfigs: $result")
        return result
    }

    fun validateProduct(product: Product, selectedLanguage: String) {
        val filter = _activeFilter.value
        Log.d("FilterDebug", "validateProduct called with product=$product, filter=$filter, lang=$selectedLanguage")
        _filterViolations.value = filterCheckUseCase(product, filter, selectedLanguage)
        Log.d("FilterDebug", "validateProduct filterViolations=${_filterViolations.value}")
    }

    fun updateSearchText(newText: String) {
        Log.d("FilterDebug", "updateSearchText: $newText")
        _searchText.value = newText
    }
}