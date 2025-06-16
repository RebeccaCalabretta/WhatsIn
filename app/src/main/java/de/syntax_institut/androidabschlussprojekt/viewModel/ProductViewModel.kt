package de.syntax_institut.androidabschlussprojekt.viewModel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<String?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()
}