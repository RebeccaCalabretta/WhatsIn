package de.syntax_institut.androidabschlussprojekt.data.local.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.utils.toProductType

@Entity(tableName = "scanned_product")
data class ScannedProduct(
    @PrimaryKey val barcode: String,
    val name: String,
    val brand: String? = null,
    val corporation: String? = null,
    val imageUrl: String?,
    val productType: String,
    val ingredients: String? = null,
    val allergens: String? = null,
    val additives: String? = null,
    val labels: String? = null,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

fun ScannedProduct.toProduct(): Product {
    Log.d("ScannedProduct", "Lese aus DB: barcode=$barcode mit Typ-String='$productType'")

    return Product(
        barcode = barcode,
        name = name,
        brand = brand,
        corporation = corporation,
        imageUrl = imageUrl,
        productType = productType.toProductType(),
        ingredientsTags = ingredients?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        additivesTags = additives?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        allergensTags = allergens?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        labelsTags = labels?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        countriesTags = emptyList(),
        ingredientsText = null,
        additivesText = null,
        allergensText = null,
        nutriScore = null,
        nutriments = Nutriments(),
        isFavorite = isFavorite,
        timestamp = timestamp
    )
}
