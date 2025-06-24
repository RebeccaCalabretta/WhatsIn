package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.ApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem

class DefaultFilterRepository(
    private val api: ApiService
) : FilterRepository {

    override suspend fun fetchLabels(): List<FilterItem> {
        return api.getLabels().filters
    }

    override suspend fun fetchAdditives(): List<FilterItem> {
        return api.getAdditives().filters
    }

    override suspend fun fetchAllergens(): List<FilterItem> {
        return api.getAllergens().filters
    }

    override suspend fun fetchIngredients(): List<FilterItem> {
        return api.getIngredients().filters
    }
}