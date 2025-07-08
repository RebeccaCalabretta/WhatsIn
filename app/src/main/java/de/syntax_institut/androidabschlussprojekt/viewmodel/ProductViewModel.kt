package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: DefaultProductRepository
) : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _productError = MutableStateFlow<ProductError?>(null)
    val productError = _productError.asStateFlow()

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage: SharedFlow<String> = _snackbarMessage

    var recentlyDeletedProduct: Product? = null
        private set

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

    fun loadProductFromDatabase(barcode: String) {
        viewModelScope.launch {
            val localProduct = repository.getProductFromDatabase(barcode)
            _selectedProduct.value = localProduct
            Log.d(
                "DetailScreen",
                "Produkt aus DB geladen: ${localProduct.name}, isFavorite=${localProduct.isFavorite}"
            )
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
            Log.d("Favorite", "Setze Favorite auf ${updated.isFavorite} für ${updated.barcode}")
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
            _snackbarMessage.emit("Produkt gelöscht")
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            recentlyDeletedProduct?.let { product ->
                repository.saveScannedProduct(product)
                recentlyDeletedProduct = null
                _snackbarMessage.emit("Produkt wiederhergestellt")
            }
        }
    }
}