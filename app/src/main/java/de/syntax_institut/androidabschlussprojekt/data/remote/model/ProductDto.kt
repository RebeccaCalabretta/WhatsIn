package de.syntax_institut.androidabschlussprojekt.data.remote.model

import com.squareup.moshi.Json

data class ProductDto(
    @Json(name = "code") val barcode: String,
    @Json(name = "product_name_de") val productNameDe: String?,
    @Json(name = "product_name_en") val productNameEn: String?,
    @Json(name = "product_name") val name: String?,
    @Json(name = "brands") val brand: String?,
    @Json(name = "image_front_url") val imageUrl: String?,
    @Json(name = "ingredients_text_de") val ingredientsTextDe: String?,
    @Json(name = "ingredients_text_en") val ingredientsTextEn: String?,
    @Json(name = "ingredients_text") val ingredientsText: String?,
    @Json(name = "nutriments") val nutriments: NutrimentsDto?,
    @Json(name = "labels_tags") val labelsTags: List<String>?,
    @Json(name = "additives_tags") val additivesTags: List<String>?,
    @Json(name = "allergens_tags") val allergensTags: List<String>?,
    @Json(name = "nutrition_grades") val nutritionGrades: String?,
    @Json(name = "countries_tags") val countriesTags: List<String>?,
    @Json(name = "categories_tags") val categoriesTags: List<String>?
)
