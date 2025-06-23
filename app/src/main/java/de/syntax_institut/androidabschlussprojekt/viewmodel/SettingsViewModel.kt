package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _activeFilter = MutableStateFlow(ActiveFilter())
    val activeFilter: StateFlow<ActiveFilter> = _activeFilter

    init {
        Log.d("SettingsViewModel", "SettingsViewModel gestartet – Filter wird vorbereitet")
        viewModelScope.launch {
            loadFilterFromDataStore()
        }
    }

    private fun loadFilterFromDataStore() {
        Log.d("SettingsViewModel", "loadFilterFromDataStore() aufgerufen")
    }
}