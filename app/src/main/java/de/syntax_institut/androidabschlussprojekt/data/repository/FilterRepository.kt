package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterItem

interface FilterRepository {
    suspend fun fetchLabels(): List<FilterItem>
    suspend fun fetchAdditives(): List<FilterItem>
    suspend fun fetchAllergens(): List<FilterItem>
    suspend fun fetchIngredients(): List<FilterItem>
}