package de.syntax_institut.androidabschlussprojekt.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScanRoute

@Serializable
object FoodRoute

@Serializable
object BeautyRoute

@Serializable
object SettingsRoute

@Serializable
object FilterRoute

@Serializable
data class DetailRoute(
    val barcode: String,
    val fromScan: Boolean = false
)