package de.syntax_institut.androidabschlussprojekt.data.remote.model

import de.syntax_institut.androidabschlussprojekt.data.mapping.CorporationMapper
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.model.Product

fun ProductDto.toProduct(productType: ProductType): Product {

    val allBrands = (brandsTags ?: emptyList()) + (brand?.split(",")?.map { it.trim() } ?: emptyList())

    val corporation = allBrands
        .mapNotNull { CorporationMapper.getCorporationForBrand(it) }
        .firstOrNull()


    return Product(
        barcode = barcode,
        name = productNameDe ?: productNameEn ?: name,
        brand = brand,
        corporation = corporation,
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