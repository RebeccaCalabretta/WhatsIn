package de.syntax_institut.androidabschlussprojekt.data

import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.model.Product

val dummyProduct = Product(
    barcode = "4058172786112",
    name = "dmBio Beeren Crunchy Müsli",
    brand = "dmBio",
    imageUrl = "",
    ingredients = """
        Haferflocken 66%, Reissirup, Rapsöl, Zuckerrüben (Zucker),
        Dinkel-Wheat puffed, Cranberry-Konzentrat 2%,
        Gefriertrocknete Beeren 1,5%, Zitronensaftkonzentrat, Meersalz
    """.trimIndent(),
    nutriments = Nutriments(
        energyKcal = 411.0,
        fat = 14.0,
        saturatedFat = 1.7,
        carbohydrates = 58.0,
        fiber = 7.1,
        proteins = 9.4,
        salt = 0.2,
        sugars = 16.0
    )
)