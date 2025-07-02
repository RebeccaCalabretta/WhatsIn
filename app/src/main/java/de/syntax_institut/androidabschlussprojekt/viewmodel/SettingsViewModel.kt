package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.ui.theme.AppColorScheme
import de.syntax_institut.androidabschlussprojekt.utils.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsViewModel(
    private val context: Context
) : ViewModel() {

    private val DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")

    private val _isDarkmode = MutableStateFlow(false)
    val isDarkmode: StateFlow<Boolean> = _isDarkmode

    private val _appColorScheme = MutableStateFlow(AppColorScheme.Orange)
    val appColorScheme: StateFlow<AppColorScheme> = _appColorScheme


    init {
        viewModelScope.launch {
            context.dataStore.data
                .map { prefs -> prefs[DARK_MODE_KEY] ?: false }
                .collect { value -> _isDarkmode.value = value }
        }
    }

    fun toggleDarkmode() {
        val newValue = !_isDarkmode.value
        _isDarkmode.value = newValue

        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                prefs[DARK_MODE_KEY] = newValue
            }
        }
    }
}