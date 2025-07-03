package de.syntax_institut.androidabschlussprojekt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.model.toProduct
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterCheckUseCase
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val repository: ProductRepository,
    private val filterCheckUseCase: FilterCheckUseCase,
    private val filterRepository: FilterRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts

    private val _activeFilter = MutableStateFlow(ActiveFilter())
    val activeFilter: StateFlow<ActiveFilter> = _activeFilter

    init {
        viewModelScope.launch {
            _activeFilter.value = filterRepository.getActiveFilter()
        }
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            repository.getScannedProducts()
                .map { list -> list.map { it.toProduct() } }
                .collect { _allProducts.value = it }
        }
    }

    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }

    val filteredProducts: StateFlow<List<Product>> = searchText
        .combine(allProducts) { text, products ->
            if (text.isBlank()) {
                products
            } else {
                val query = text.trim().lowercase()
                products.filter { product ->
                    product.name?.contains(query, ignoreCase = true) == true ||
                            product.ingredientsText?.contains(query, ignoreCase = true) == true ||
                            product.labelsTags.any { it.contains(query, ignoreCase = true) } ||
                            product.allergensTags.any { it.contains(query, ignoreCase = true) }
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filterViolationsMap: StateFlow<Map<String, List<String>>> =
        filteredProducts
            .combine(activeFilter) { products, filter ->
                products.associate { product ->
                    product.barcode to filterCheckUseCase(product, filter)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyMap()
            )

    fun getProductsByType(type: ProductType): StateFlow<List<Product>> {
        return filteredProducts
            .map { list -> list.filter { it.productType == type } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = emptyList()
            )
    }
}
