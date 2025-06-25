package de.syntax_institut.androidabschlussprojekt.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilterListResponse(
    @Json(name = "tags")
    val tags: List<FilterItem> = emptyList()
)

@JsonClass(generateAdapter = true)
data class FilterItem(
    val id: String,
    val name: String? = null,
    val products: Int = 0
)