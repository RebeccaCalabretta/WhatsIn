package de.syntax_institut.androidabschlussprojekt.viewModel


import androidx.lifecycle.ViewModel
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _scannedEan = MutableStateFlow<String?>(null)
    val scannedEan = _scannedEan.asStateFlow()

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun startScan(ean: String) {
        _scannedEan.value = ean
    }
}