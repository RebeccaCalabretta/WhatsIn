package de.syntax_institut.androidabschlussprojekt.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _scannedProducts = MutableStateFlow<List<ScannedProduct>>(emptyList())
    val scannedProducts: StateFlow<List<ScannedProduct>> = _scannedProducts

    private val _productError = MutableStateFlow<ProductError?>(null)
    val productError = _productError.asStateFlow()

    init {
        loadScannedProducts()
    }

    private fun loadScannedProducts() {
        viewModelScope.launch {
            repository.getScannedProducts()
                .collect { products ->
                    _scannedProducts.value = products
                    Log.d("ProductViewModel", "Scanned products geladen (${products.size})")
                }
        }
    }

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

                if (product == null) {
                    Log.d("ProductViewModel", "Kein Produkt gefunden für Barcode: $barcode")
                    _selectedProduct.value = null
                    _productError.value = ProductError.NOT_FOUND
                    return@launch
                }

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
}