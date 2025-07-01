package de.syntax_institut.androidabschlussprojekt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    private val _allProducts = MutableStateFlow<List<ScannedProduct>>(emptyList())
    val allProducts: StateFlow<List<ScannedProduct>> = _allProducts

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