package de.syntax_institut.androidabschlussprojekt.viewmodel

import android.app.Activity
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax_institut.androidabschlussprojekt.ui.theme.AppColorScheme
import de.syntax_institut.androidabschlussprojekt.utils.dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsViewModel(
    private val context: Context
) : ViewModel() {

    private val DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
    private val LANGUAGE_KEY = stringPreferencesKey("selected_language")

    private val _isDarkmode = MutableStateFlow(false)
    val isDarkmode: StateFlow<Boolean> = _isDarkmode

    private val _selectedLanguage = MutableStateFlow("de")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _appColorScheme = MutableStateFlow(AppColorScheme.Orange)
    val appColorScheme: StateFlow<AppColorScheme> = _appColorScheme

    init {
        viewModelScope.launch {
            context.dataStore.data
                .map { prefs -> prefs[DARK_MODE_KEY] ?: false }
                .collect { value -> _isDarkmode.value = value }
        }
        viewModelScope.launch {
            context.dataStore.data
                .map { prefs -> prefs[LANGUAGE_KEY] ?: "de" }
                .collect { value -> _selectedLanguage.value = value }
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

    fun setLanguage(langCode: String, activity: Activity) {
        _selectedLanguage.value = langCode
        setAppLocale(activity, langCode)
        viewModelScope.launch {
            activity.dataStore.edit { prefs ->
                prefs[LANGUAGE_KEY] = langCode
            }
        }
        activity.recreate()
    }

    fun setAppLocale(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}