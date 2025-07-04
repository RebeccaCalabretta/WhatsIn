package de.syntax_institut.androidabschlussprojekt.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: DefaultProductRepository
) : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _productError = MutableStateFlow<ProductError?>(null)
    val productError = _productError.asStateFlow()

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }

    fun startScan(barcode: String) {
        if (barcode.isBlank()) {
            Log.d("ProductViewModel", "Ungültiger Barcode")
            return
        }
        Log.d("ProductViewModel", "Scan gestartet mit Barcode: $barcode")
        getProductByBarcode(barcode)
    }

    fun clearProductError() {
        _productError.value = null
    }

    fun getProductByBarcode(barcode: String) {
        Log.d("ProductViewModel", "API-Aufruf für Barcode: $barcode")

        viewModelScope.launch {
            try {
                val product = repository.fetchProductByBarcode(barcode)

                _selectedProduct.value = product
                clearProductError()
                Log.d("ProductViewModel", "Produkt geladen: ${product.name}")

                repository.saveScannedProduct(product)
                Log.d("ProductViewModel", "Produkt wurde gespeichert: ${product.name}")

            } catch (e: ProductException) {
                _selectedProduct.value = null
                handleError(e.error, e.message ?: "Fehler beim Laden des Produkts")
            }
        }
    }

    private fun handleError(error: ProductError, message: String) {
        _productError.value = error
        Log.e("ProductViewModel", message)
    }

    fun toggleFavorite() {
        _selectedProduct.value?.let { product ->
            val updated = product.copy(isFavorite = !product.isFavorite)
            _selectedProduct.value = updated
            viewModelScope.launch {
                repository.updateFavorite(updated.barcode, updated.isFavorite)
            }
        }
    }
}