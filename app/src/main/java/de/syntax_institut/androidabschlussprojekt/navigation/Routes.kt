package de.syntax_institut.androidabschlussprojekt.navigation

import kotlinx.serialization.Serializable

@Serializable
object ScanRoute

@Serializable
object FoodListRoute

@Serializable
object BeautyListRoute

@Serializable
data class DetailRoute(val barcode: String)