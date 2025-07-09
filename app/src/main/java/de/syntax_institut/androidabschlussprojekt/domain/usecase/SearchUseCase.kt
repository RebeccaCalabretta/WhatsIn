package de.syntax_institut.androidabschlussprojekt.domain.usecase

import de.syntax_institut.androidabschlussprojekt.model.Product

class SearchUseCase {
    operator fun invoke(products: List<Product>, query: String): List<Product> {
        if (query.isBlank()) return products
        val lowerQuery = query.trim().lowercase()
        return products.filter { product ->
            product.name?.contains(lowerQuery, ignoreCase = true) == true ||
                    product.ingredientsText?.contains(lowerQuery, ignoreCase = true) == true ||
                    product.labelsTags.any { it.contains(lowerQuery, ignoreCase = true) } ||
                    product.allergensTags.any { it.contains(lowerQuery, ignoreCase = true) }
        }
    }
}