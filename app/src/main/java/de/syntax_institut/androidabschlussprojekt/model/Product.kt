package de.syntax_institut.androidabschlussprojekt.model

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.helper.ProductType

data class Product(
    val barcode: String,
    val name: String? = null,
    val brand: String? = null,
    val corporation: String? = null,
    val imageUrl: String? = null,
    val ingredientsText: String? = null,
    val additivesText: String? = null,
    val allergensText: String? = null,
    val nutriScore: String? = null,
    val nutriments: Nutriments = Nutriments(),
    val ingredientsTags: List<String> = emptyList(),
    val additivesTags: List<String> = emptyList(),
    val allergensTags: List<String> = emptyList(),
    val labelsTags: List<String> = emptyList(),
    val countriesTags: List<String> = emptyList(),
    val productType: ProductType,
    val isFavorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

fun Product.toScannedProduct(): ScannedProduct {
    Log.d("ProductModel", "Konvertiere zu ScannedProduct mit Typ: ${productType.name.lowercase()}")

    return ScannedProduct(
        barcode = barcode,
        name = name ?: "Unbekannt",
        brand = brand,
        corporation = corporation,
        imageUrl = imageUrl,
        productType = productType.name.lowercase(),

        ingredients = ingredientsTags.joinToString(";"),
        ingredientsText = ingredientsText,
        ingredientsTextDe = null,
        ingredientsTextEn = null,

        additives = additivesTags.joinToString(";"),
        additivesText = additivesText,
        additivesTextDe = null,
        additivesTextEn = null,

        allergens = allergensTags.joinToString(";"),
        allergensText = allergensText,
        allergensTextDe = null,
        allergensTextEn = null,

        labels = labelsTags.joinToString(";"),
        countries = countriesTags.joinToString(";"),
        nutriScore = nutriScore,

        energyKcal = nutriments.energyKcal,
        fat = nutriments.fat,
        saturatedFat = nutriments.saturatedFat,
        carbohydrates = nutriments.carbohydrates,
        fiber = nutriments.fiber,
        proteins = nutriments.proteins,
        salt = nutriments.salt,
        sugars = nutriments.sugars,

        isFavorite = isFavorite,
        timestamp = timestamp
    )
}