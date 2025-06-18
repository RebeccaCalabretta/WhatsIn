package de.syntax_institut.androidabschlussprojekt.data.remote.model

import com.squareup.moshi.Json

data class NutrimentsDto(
    @Json(name = "energy-kcal_100g") val energyKcalPer100g: Double?,
    @Json(name = "fat_100g") val fatPer100g: Double?,
    @Json(name = "saturated-fat_100g") val saturatedFatPer100g: Double?,
    @Json(name = "carbohydrates_100g") val carbsPer100g: Double?,
    @Json(name = "fiber_100g") val fiberPer100g: Double?,
    @Json(name = "proteins_100g") val proteinsPer100g: Double?,
    @Json(name = "salt_100g") val saltPer100g: Double?,
    @Json(name = "sugars_100g") val sugarsPer100g: Double?
)
