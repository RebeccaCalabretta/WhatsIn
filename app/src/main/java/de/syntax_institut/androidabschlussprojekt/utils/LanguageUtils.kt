package de.syntax_institut.androidabschlussprojekt.utils

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import java.util.Locale

suspend fun Context.applySavedLocale(): Context {
    val LANGUAGE_KEY = stringPreferencesKey("selected_language")
    val prefs = dataStore.data.first()
    val lang = prefs[LANGUAGE_KEY] ?: "de"
    val locale = Locale(lang)
    Locale.setDefault(locale)

    val config = resources.configuration
    config.setLocale(locale)

    return createConfigurationContext(config)
}