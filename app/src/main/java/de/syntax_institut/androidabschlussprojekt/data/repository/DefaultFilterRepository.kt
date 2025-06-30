package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.FoodFilterApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem

class DefaultFilterRepository(
    private val api: FoodFilterApiService
) : FilterRepository {

    override suspend fun fetchAllergens(): List<FilterItem> {
        return api.getAllergens().tags
    }

    override suspend fun fetchAdditives(): List<FilterItem> {
        return api.getAdditives().tags
    }

    override suspend fun fetchLabels(): List<FilterItem> {
        return api.getLabels().tags
    }

    override suspend fun fetchCountries(): List<FilterItem> {
        return api.getCountries().tags
    }
    override suspend fun fetchBrands(): List<FilterItem> {
        return api.getBrands().tags
    }
}