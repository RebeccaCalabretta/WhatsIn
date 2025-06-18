package de.syntax_institut.androidabschlussprojekt.model

data class Product(
    val barcode: String,
    val name: String?,
    val brand: String?,
    val imageUrl: String?,
    val ingredients: String?
)
