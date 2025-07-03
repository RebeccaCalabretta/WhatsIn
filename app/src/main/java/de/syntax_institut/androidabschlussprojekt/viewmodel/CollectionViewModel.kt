package de.syntax_institut.androidabschlussprojekt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.data.local.model.toProduct
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterCheckUseCase
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val repository: DefaultProductRepository,
    private val filterCheckUseCase: FilterCheckUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _allProducts = MutableStateFlow<List<ScannedProduct>>(emptyList())
    val allProducts: StateFlow<List<ScannedProduct>> = _allProducts

    val filteredProducts: StateFlow<List<ScannedProduct>> = searchText
        .combine(allProducts) { text, products ->
            if (text.isBlank()) {
                products
            } else {
                val query = text.trim().lowercase()
                products.filter { product ->
                    product.name.contains(query, ignoreCase = true) == true ||
                            product.ingredients?.contains(query, ignoreCase = true) == true ||
                            product.labels?.contains(query, ignoreCase = true) == true ||
                            product.allergens?.contains(query, ignoreCase = true) == true
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    val filteredFoodProducts: StateFlow<List<Product>> = filteredProducts
        .map { list ->
            list.map { it.toProduct() }
                .filter { it.productType == ProductType.FOOD }
                .sortedBy { it.name?.lowercase() ?: "" }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val filteredBeautyProducts: StateFlow<List<Product>> = filteredProducts
        .map { list ->
            list.map { it.toProduct() }
                .filter { it.productType == ProductType.BEAUTY }
                .sortedBy { it.name?.lowercase() ?: "" }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    init {
        loadProducts()
    }

    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }

    fun loadProducts() {
        viewModelScope.launch {
            repository.getScannedProducts().collect { products ->
                _allProducts.value = products
            }
        }
    }
}