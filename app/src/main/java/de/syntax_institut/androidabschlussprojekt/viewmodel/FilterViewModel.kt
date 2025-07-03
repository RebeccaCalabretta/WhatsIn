package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterCheckUseCase
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterConfigUseCase
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
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

    private val _filterViolations = MutableStateFlow<List<String>>(emptyList())
    val filterViolations: StateFlow<List<String>> = _filterViolations

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        Log.d("FilterViewModel", "FilterViewModel gestartet – Filter wird vorbereitet")
        viewModelScope.launch {
            _activeFilter.value = filterRepository.getActiveFilter()
            _isLoading.value = false
        }
    }


    fun updateFilter(newFilter: ActiveFilter) {
        _activeFilter.value = newFilter
        viewModelScope.launch {
            filterRepository.saveActiveFilter(newFilter)
        }
    }

    fun buildFilterConfigs(): List<FilterConfig> {
        val active = activeFilter.value
        return filterConfigUseCase(active) { updatedFilter ->
            updateFilter(updatedFilter)
        }
    }

    fun validateProduct(product: Product) {
        val filter = _activeFilter.value
        _filterViolations.value = filterCheckUseCase(product, filter)
    }
}
