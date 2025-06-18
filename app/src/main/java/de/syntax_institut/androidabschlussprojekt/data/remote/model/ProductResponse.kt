package de.syntax_institut.androidabschlussprojekt.data.remote.model

import com.squareup.moshi.Json

data class ProductResponse(
    @Json(name = "product") val product: ProductDto?
)