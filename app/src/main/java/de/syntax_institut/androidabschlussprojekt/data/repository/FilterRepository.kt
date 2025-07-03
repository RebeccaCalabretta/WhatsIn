package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.model.ActiveFilter

interface FilterRepository {
    suspend fun  getActiveFilter(): ActiveFilter
    suspend fun saveActiveFilter(filter: ActiveFilter)
}