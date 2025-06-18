package de.syntax_institut.androidabschlussprojekt.data.remote.model

import com.squareup.moshi.Json

data class Product(
    @Json(name = "code") val barcode: String,
    @Json(name = "product_name") val name: String?,
    @Json(name = "brands") val brand: String?,
    @Json(name = "image_front_url") val imageUrl: String?,
    @Json(name = "ingredients_text") val ingredients: String?
)
