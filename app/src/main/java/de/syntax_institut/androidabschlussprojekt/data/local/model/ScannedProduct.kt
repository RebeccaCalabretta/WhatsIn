package de.syntax_institut.androidabschlussprojekt.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScannedProduct(
    @PrimaryKey val barcode: String,
    val name: String,
    val imageUrl: String?,
    val timestamp: Long,
    val productType: String,
    val ingredients: String? = null,
    val allergens: String? = null,
    val additives: String? = null,
    val labels: String? = null
)
