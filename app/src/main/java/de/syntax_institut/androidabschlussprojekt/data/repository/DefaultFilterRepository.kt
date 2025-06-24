package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.ApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem

class DefaultFilterRepository(
    private val api: ApiService
) : FilterRepository {

    override suspend fun fetchAllergens(): List<FilterItem> {
        return api.getAllergens().filters
    }

    override suspend fun fetchAdditives(): List<FilterItem> {
        return api.getAdditives().filters
    }

    override suspend fun fetchLabels(): List<FilterItem> {
        return api.getLabels().filters
    }

    override suspend fun fetchCountries(): List<FilterItem> {
        return api.getCountries().filters
    }
    override suspend fun fetchBrands(): List<FilterItem> {
        return api.getBrands().filters
    }

}