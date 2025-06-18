package de.syntax_institut.androidabschlussprojekt.data.remote.model

import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        barcode = barcode,
        name = productNameDe ?: productNameEn ?: name ?: "Kein Name verfügbar",
        brand = brand ?: "Keine Marke verfügbar",
        imageUrl = imageUrl,
        ingredients = ingredientsTextDe ?: ingredientsTextEn ?: ingredientsText ?: "Keine Zutaten verfügbar",
        nutriments = nutriments?.toNutriments() ?: Nutriments(null, null, null, null, null, null, null, null)
    )
}