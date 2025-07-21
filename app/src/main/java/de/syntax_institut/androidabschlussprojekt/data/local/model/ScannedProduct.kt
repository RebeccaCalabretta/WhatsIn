package de.syntax_institut.androidabschlussprojekt.data.local.model

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
    val ingredientsTextDe: String? = null,
    val ingredientsText: String? = null,
    val ingredientsTextEn: String? = null,

    val additives: String? = null,
    val additivesTextDe: String? = null,
    val additivesText: String? = null,
    val additivesTextEn: String? = null,

    val allergens: String? = null,
    val allergensTextDe: String? = null,
    val allergensText: String? = null,
    val allergensTextEn: String? = null,

    val energyKcal: Double? = null,
    val fat: Double? = null,
    val saturatedFat: Double? = null,
    val carbohydrates: Double? = null,
    val fiber: Double? = null,
    val proteins: Double? = null,
    val salt: Double? = null,
    val sugars: Double? = null,

    val labels: String? = null,
    val countries: String? = null,
    val nutriScore: String? = null,

    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

fun ScannedProduct.toProduct(): Product {

    return Product(
        barcode = barcode,
        name = name,
        brand = brand,
        corporation = corporation,
        imageUrl = imageUrl,
        productType = productType.toProductType(),

        ingredientsTags = ingredients?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        ingredientsText = ingredientsTextDe
            ?.takeIf { it.isNotBlank() }
            ?: ingredientsText
                ?.takeIf { it.isNotBlank() }
            ?: ingredientsTextEn,

        additivesTags = additives?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        additivesText = additivesTextDe
            ?.takeIf { it.isNotBlank() }
            ?: additivesText
                ?.takeIf { it.isNotBlank() }
            ?: additivesTextEn,

        allergensTags = allergens?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        allergensText = allergensTextDe
            ?.takeIf { it.isNotBlank() }
            ?: allergensText
                ?.takeIf { it.isNotBlank() }
            ?: allergensTextEn,

        labelsTags = labels?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),
        countriesTags = countries?.split(";")?.filter { it.isNotBlank() } ?: emptyList(),

        nutriScore = nutriScore,
        nutriments = Nutriments(
            energyKcal = energyKcal,
            fat = fat,
            saturatedFat = saturatedFat,
            carbohydrates = carbohydrates,
            fiber = fiber,
            proteins = proteins,
            salt = salt,
            sugars = sugars
        ),

        isFavorite = isFavorite,
        timestamp = timestamp
    )
}