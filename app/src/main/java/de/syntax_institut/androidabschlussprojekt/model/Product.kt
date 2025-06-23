package de.syntax_institut.androidabschlussprojekt.model

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
    val countriesTags: List<String> = emptyList()
)
