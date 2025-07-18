package de.syntax_institut.androidabschlussprojekt.data.remote.model

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.model.Product

fun ProductDto.toProduct(productType: ProductType): Product {
    Log.d("ProductMapper", "Mapping Nutriments: $nutriments")

    return Product(
        barcode = barcode,
        name = productNameDe ?: productNameEn ?: name,
        brand = brand,
        imageUrl = imageUrl,
        ingredientsText = ingredientsTextDe ?: ingredientsTextEn ?: ingredientsText,
        nutriScore = nutritionGrades,
        nutriments = nutriments?.toNutriments() ?: Nutriments(),

        labelsTags = labelsTags.orEmpty(),
        additivesTags = additivesTags.orEmpty(),
        allergensTags = allergensTags.orEmpty(),
        countriesTags = countriesTags.orEmpty(),

        productType = productType
    )
}