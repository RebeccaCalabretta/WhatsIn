package de.syntax_institut.androidabschlussprojekt.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {

    private val _isDarkmode = MutableStateFlow(false)
    val isDarkmode: StateFlow<Boolean> = _isDarkmode

    fun toggleDarkmode() {
        _isDarkmode.value = !_isDarkmode.value
    }
}