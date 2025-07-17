package de.syntax_institut.androidabschlussprojekt.viewmodel

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

class SettingsViewModel(
    context: Context
) : ViewModel() {

    private val dataStore = context.applicationContext.dataStore

    private val DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
    private val LANGUAGE_KEY = stringPreferencesKey("selected_language")
    private val COLOR_SCHEME_KEY = stringPreferencesKey("selected_color_scheme")
    private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")

    private val _isDarkmode = MutableStateFlow(false)
    val isDarkmode: StateFlow<Boolean> = _isDarkmode

    private val _selectedLanguage = MutableStateFlow("de")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _appColorScheme = MutableStateFlow(AppColorScheme.Mint)
    val appColorScheme: StateFlow<AppColorScheme> = _appColorScheme

    private val _isColorSchemeLoaded = MutableStateFlow(false)
    val isColorSchemeLoaded: StateFlow<Boolean> = _isColorSchemeLoaded

    private val _onboardingCompleted = MutableStateFlow(false)
    val onboardingCompleted: StateFlow<Boolean> = _onboardingCompleted

    init {
        viewModelScope.launch {
            dataStore.data
                .map { prefs -> prefs[DARK_MODE_KEY] ?: false }
                .collect { value -> _isDarkmode.value = value }
        }
        viewModelScope.launch {
            dataStore.data
                .map { prefs -> prefs[LANGUAGE_KEY] ?: "de" }
                .collect { value -> _selectedLanguage.value = value }
        }
        viewModelScope.launch {
            dataStore.data
                .map { prefs -> prefs[COLOR_SCHEME_KEY] ?: AppColorScheme.Mint.name }
                .collect { value ->
                    _appColorScheme.value = AppColorScheme.valueOf(value)
                    _isColorSchemeLoaded.value = true
                }
        }
        viewModelScope.launch {
            dataStore.data
                .map { prefs -> prefs[ONBOARDING_COMPLETED_KEY] ?: false }
                .collect { value -> _onboardingCompleted.value = value }
        }
    }

    fun toggleDarkmode() {
        val newValue = !_isDarkmode.value
        _isDarkmode.value = newValue
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[DARK_MODE_KEY] = newValue
            }
        }
    }

    fun setLanguage(langCode: String, activity: android.app.Activity) {
        viewModelScope.launch {
            activity.dataStore.edit { prefs ->
                prefs[LANGUAGE_KEY] = langCode
            }
            activity.runOnUiThread { activity.recreate() }
        }
    }

    fun setAppColorScheme(scheme: AppColorScheme) {
        _appColorScheme.value = scheme
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[COLOR_SCHEME_KEY] = scheme.name
            }
        }
    }

    fun setOnboardingCompleted(completed: Boolean) {
        _onboardingCompleted.value = completed
        viewModelScope.launch {
            dataStore.edit { prefs ->
                prefs[ONBOARDING_COMPLETED_KEY] = completed
            }
        }
    }
}