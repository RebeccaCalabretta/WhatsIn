package de.syntax_institut.androidabschlussprojekt.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _scannedBarcode = MutableStateFlow<String?>(null)
    val scannedBarcode = _scannedBarcode.asStateFlow()

    private val _productError = MutableStateFlow<ProductError?>(null)
    val productError = _productError.asStateFlow()

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
        Log.d("ProductViewModel", "Produkt ausgewählt: ${product.name}")
    }

    fun startScan(barcode: String) {
        if (barcode.isBlank()) {
            Log.d("ProductViewModel", "Ungültiger Barcode")
            return
        }
        _scannedBarcode.value = barcode
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