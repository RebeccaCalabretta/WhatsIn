package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun fetchProductByBarcode(barcode: String): Product?

    suspend fun saveScannedProduct(product: Product)

    suspend fun getScannedProducts(): Flow<List<ScannedProduct>>

}