package de.syntax_institut.androidabschlussprojekt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterCheckUseCase
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterConfigUseCase
import de.syntax_institut.androidabschlussprojekt.error.FilterError
import de.syntax_institut.androidabschlussprojekt.error.FilterException
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

    private val _filterError = MutableStateFlow<FilterError?>(null)
    val filterError: StateFlow<FilterError?> = _filterError

    init {
        viewModelScope.launch {
            try {
                _activeFilter.value = filterRepository.getActiveFilter()
            } catch (e: FilterException) {
                _filterError.value = e.error
            }
            _isLoading.value = false
        }
    }

    fun updateFilter(newFilter: ActiveFilter) {
        _activeFilter.value = newFilter
        viewModelScope.launch {
            try {
                filterRepository.saveActiveFilter(newFilter)
            } catch (e: FilterException) {
                _filterError.value = e.error
            }
        }
    }

    fun buildFilterConfigs(
        searchText: String,
        selectedLanguage: String,
        allFiltersChip: String
    ): List<FilterConfig> {
        val active = activeFilter.value
        return filterConfigUseCase(
            active,
            searchText,
            selectedLanguage,
            allFiltersChip,
            onUpdateFilter = { updateFilter(it) }
        )
    }


    fun validateProduct(product: Product, selectedLanguage: String) {
        val filter = _activeFilter.value
        _filterViolations.value = filterCheckUseCase(product, filter, selectedLanguage)
    }

    fun resetAllFilters() {
        _activeFilter.value = ActiveFilter()
        _searchText.value = ""
        viewModelScope.launch {
            filterRepository.saveActiveFilter(_activeFilter.value)
        }
    }

    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }

    fun clearFilterError() {
        _filterError.value = null
    }
}