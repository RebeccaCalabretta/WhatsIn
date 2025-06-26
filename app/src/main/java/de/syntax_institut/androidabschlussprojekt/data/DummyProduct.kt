package de.syntax_institut.androidabschlussprojekt.data

import de.syntax_institut.androidabschlussprojekt.model.Nutriments
import de.syntax_institut.androidabschlussprojekt.model.Product

val dummyProduct = Product(
    barcode = "4058172333378",
    name = "Gemüsebrühe",
    brand = "dmBio",
    imageUrl = "https://images.openfoodfacts.org/images/products/405/817/233/3378/front_de.37.400.jpg",
    ingredientsText = """
        Meersalz, Maisstärke*, Gemüse* 13,5 % (Zwiebel*, Karotten*, Sellerie*, Lauch*, Petersilie*),
        Hefeeextrakt*, Sonnenblumenöl*, Kurkuma*, Muskatnuss*, Liebstöckel*, schwarzer Pfeffer*.
        *aus biologischer Landwirtschaft
    """.trimIndent(),
    nutriments = Nutriments(
        energyKcal = 214.0,
        fat = 2.3,
        saturatedFat = 0.5,
        carbohydrates = 34.0,
        fiber = 1.2,
        proteins = 10.2,
        salt = 42.0,
        sugars = 9.8
    )
)