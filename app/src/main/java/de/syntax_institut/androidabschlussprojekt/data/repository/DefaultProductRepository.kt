package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.ApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.toProduct
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product

class DefaultProductRepository(
    private val api: ApiService
) : ProductRepository {
    override suspend fun fetchProductByBarcode(barcode: String): Product {
        val response = api.getProductByBarcode(barcode)
        val productDto = response.product

        if (productDto == null) {
            throw ProductException(ProductError.NOT_FOUND)
        }

        return productDto.toProduct()
    }
}
