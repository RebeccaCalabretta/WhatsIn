package de.syntax_institut.androidabschlussprojekt.data.remote.model

data class FilterListResponse(
    val filters: List<FilterItem>
)

data class FilterItem(
    val id: String,
    val name: String,
    val products: Int
)