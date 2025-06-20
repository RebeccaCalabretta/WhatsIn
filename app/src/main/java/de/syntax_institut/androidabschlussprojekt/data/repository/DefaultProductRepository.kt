package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.remote.ApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.toProduct
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product
import retrofit2.HttpException

class DefaultProductRepository(
    private val api: ApiService
) : ProductRepository {

    override suspend fun fetchProductByBarcode(barcode: String): Product {
        return try {
            val response = api.getProductByBarcode(barcode)
            val productDto = response.product

            if (productDto == null) {
                throw ProductException(ProductError.NOT_FOUND)
            }

            productDto.toProduct()

        } catch (e: HttpException) {
            if (e.code() == 404) {
                throw ProductException(ProductError.NOT_FOUND)
            } else {
                throw ProductException(ProductError.NETWORK)
            }
        } catch (e: Exception) {
            throw ProductException(ProductError.UNKNOWN)
        }
    }
}
