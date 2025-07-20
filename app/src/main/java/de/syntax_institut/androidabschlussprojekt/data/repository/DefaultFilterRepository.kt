package de.syntax_institut.androidabschlussprojekt.data.repository

import android.content.Context
import de.syntax_institut.androidabschlussprojekt.error.FilterError
import de.syntax_institut.androidabschlussprojekt.error.FilterException
import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter
import de.syntax_institut.androidabschlussprojekt.utils.filter.loadFilterFromDataStore
import de.syntax_institut.androidabschlussprojekt.utils.filter.saveFilterToDataStore
import kotlinx.serialization.json.Json

class DefaultFilterRepository(
    private val context: Context
) : FilterRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    override suspend fun getActiveFilter(): ActiveFilter {
        try {
            return loadFilterFromDataStore(context, json)
        } catch (e: Exception) {
            throw FilterException(FilterError.LOAD_FAILED)
        }
    }

    override suspend fun saveActiveFilter(filter: ActiveFilter) {
        try {
            saveFilterToDataStore(context, json, filter)
        } catch (e: Exception) {
            throw FilterException(FilterError.SAVE_FAILED)
        }
    }
}