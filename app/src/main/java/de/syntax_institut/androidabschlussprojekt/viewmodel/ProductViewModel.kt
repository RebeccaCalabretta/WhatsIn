package de.syntax_institut.androidabschlussprojekt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class SnackEvent {
    data object ProductRemoved : SnackEvent()
    data object ProductRestored : SnackEvent()
}

class ProductViewModel(
    private val repository: DefaultProductRepository
) : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _productError = MutableStateFlow<ProductError?>(null)
    val productError = _productError.asStateFlow()

    private val _snackbarEvent = MutableSharedFlow<SnackEvent>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

    private var recentlyDeletedProduct: Product? = null
        private set

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }

    fun startScan(barcode: String) {
        if (barcode.isBlank()) return
        getProductByBarcode(barcode)
    }

    fun clearProductError() {
        _productError.value = null
    }

    private fun getProductByBarcode(barcode: String) {
        viewModelScope.launch {
            try {
                val product = repository.fetchProductByBarcode(barcode)

                _selectedProduct.value = product
                clearProductError()
                repository.saveScannedProduct(product)
            } catch (e: ProductException) {
                _selectedProduct.value = null
                handleError(e.error)
            }
        }
    }

    fun loadProductFromDatabase(barcode: String) {
        viewModelScope.launch {
            val localProduct = repository.getProductFromDatabase(barcode)
            _selectedProduct.value = localProduct
        }
    }

    private fun handleError(error: ProductError) {
        _productError.value = error
    }

    fun toggleFavorite() {
        _selectedProduct.value?.let { product ->
            val updated = product.copy(isFavorite = !product.isFavorite)
            _selectedProduct.value = updated
            viewModelScope.launch {
                repository.updateFavorite(updated.barcode, updated.isFavorite)
                val refreshed = repository.getProductFromDatabase(updated.barcode)
                _selectedProduct.value = refreshed
            }
        }
    }

    fun deleteProduct(barcode: String) {
        viewModelScope.launch {
            val product = repository.getProductFromDatabase(barcode)
            recentlyDeletedProduct = product
            repository.deleteProduct(barcode)
            _snackbarEvent.emit(SnackEvent.ProductRemoved)
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            recentlyDeletedProduct?.let { product ->
                repository.saveScannedProduct(product)
                recentlyDeletedProduct = null
                _snackbarEvent.emit(SnackEvent.ProductRestored)
            }
        }
    }
}