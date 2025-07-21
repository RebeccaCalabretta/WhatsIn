package de.syntax_institut.androidabschlussprojekt.utils.filter

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import de.syntax_institut.androidabschlussprojekt.error.FilterError
import de.syntax_institut.androidabschlussprojekt.error.FilterException
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.utils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val ACTIVE_FILTER_KEY = stringPreferencesKey("active_filter")

suspend fun saveFilterToDataStore(context: Context, json: Json, filter: ActiveFilter) {
    val dataStore = context.dataStore
    try {
        val jsonString = json.encodeToString(filter)
        dataStore.edit { prefs ->
            prefs[ACTIVE_FILTER_KEY] = jsonString
        }
    } catch (e: Exception) {
        throw FilterException(FilterError.SAVE_FAILED)
    }
}

suspend fun loadFilterFromDataStore(context: Context, json: Json): ActiveFilter {
    val dataStore = context.dataStore
    return try {
        val prefs = dataStore.data.first()
        val jsonString = prefs[ACTIVE_FILTER_KEY]
        if (jsonString != null) {
            json.decodeFromString(jsonString)
        } else {
            ActiveFilter()
        }
    } catch (e: Exception) {
        throw FilterException(FilterError.LOAD_FAILED)
    }
}