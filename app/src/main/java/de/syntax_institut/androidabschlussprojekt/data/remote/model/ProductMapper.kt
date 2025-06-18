package de.syntax_institut.androidabschlussprojekt.data.remote.model

import de.syntax_institut.androidabschlussprojekt.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        barcode = barcode,
        name = name,
        brand = brand,
        imageUrl = imageUrl,
        ingredients = ingredients
    )
}