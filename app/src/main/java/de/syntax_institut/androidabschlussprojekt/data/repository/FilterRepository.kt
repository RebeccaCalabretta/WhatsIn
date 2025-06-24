package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem

interface FilterRepository {
    suspend fun fetchAllergens(): List<FilterItem>
    suspend fun fetchAdditives(): List<FilterItem>
    suspend fun fetchLabels(): List<FilterItem>
    suspend fun fetchCountries(): List<FilterItem>
    suspend fun fetchBrands(): List<FilterItem>
}