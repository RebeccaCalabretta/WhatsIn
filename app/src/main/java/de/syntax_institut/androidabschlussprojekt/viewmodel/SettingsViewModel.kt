package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class SettingsViewModel(
    context: Context
) : ViewModel() {

    private val dataStore = context.dataStore

    private val _activeFilter = MutableStateFlow(ActiveFilter())
    val activeFilter: StateFlow<ActiveFilter> = _activeFilter

    private val json = Json {
        ignoreUnknownKeys = true   // Verhindert Abstürze, wenn zusätzliche Felder im gespeicherten JSON auftauchen
        encodeDefaults = true      // Speichert auch Standardwerte wie leere Listen mit
    }

    companion object {
        private val ACTIVE_FILTER_KEY = stringPreferencesKey("active_filter")
    }

    init {
        Log.d("SettingsViewModel", "SettingsViewModel gestartet – Filter wird vorbereitet")
        viewModelScope.launch {
            loadFilterFromDataStore()
        }
    }

    private suspend fun loadFilterFromDataStore() {
        try {
            val prefs = dataStore.data.first()
            val jsonString = prefs[ACTIVE_FILTER_KEY]
            val filter = if (jsonString != null) {
                json.decodeFromString<ActiveFilter>(jsonString)
            } else {
                ActiveFilter()
            }
            _activeFilter.value = filter
            Log.d("SettingsViewModel", "Filter erfolgreich geladen: $filter")
        } catch (e: Exception) {
            Log.e("SettingsViewModel", "Fehler beim Laden des Filters", e)
        }
    }

    fun saveFilterToDataStore(newFilter: ActiveFilter) {
        viewModelScope.launch {
            try {
                val jsonString = json.encodeToString(newFilter)
                dataStore.edit { prefs ->
                    prefs[ACTIVE_FILTER_KEY] = jsonString
                }
                _activeFilter.value = newFilter
                Log.d("SettingsViewModel", "Filter erfolgreich gespeichert: $newFilter")
            } catch (e: Exception) {
                Log.e("SettingsViewModel", "Fehler beim Speichern des Filters", e)
            }
        }
    }

    fun updateFilter(newFilter: ActiveFilter) {
        _activeFilter.value = newFilter
        saveFilterToDataStore(newFilter)
    }
}