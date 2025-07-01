package de.syntax_institut.androidabschlussprojekt.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CollectionViewModel: ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    fun updateSearchText(newText: String) {
        _searchText.value = newText
    }
}