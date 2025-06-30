package de.syntax_institut.androidabschlussprojekt.data.remote.model

import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.model.Product

fun ProductDto.toProduct(productType: ProductType): Product {
    return Product(
        barcode = barcode,
        name = productNameDe ?: productNameEn ?: name ?: "Kein Name verfügbar",
        brand = brand ?: "Keine Marke verfügbar",
        imageUrl = imageUrl,
        ingredientsText = ingredientsTextDe ?: ingredientsTextEn ?: ingredientsText
        ?: "Keine Zutaten verfügbar",
        nutriScore = nutritionGrades ?: "Unbekannt",
        nutriments = nutriments?.toNutriments() ?: Nutriments(),

        labelsTags = labelsTags.orEmpty(),
        additivesTags = additivesTags.orEmpty(),
        allergensTags = allergensTags.orEmpty(),
        countriesTags = countriesTags.orEmpty(),

        productType = productType
    )
}