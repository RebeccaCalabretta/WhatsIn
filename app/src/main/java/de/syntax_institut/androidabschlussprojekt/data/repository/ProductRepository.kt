package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.model.Product

interface ProductRepository {
    suspend fun fetchProductByBarcode(barcode: String): Product?

    suspend fun saveScannedProduct(product: Product)
}