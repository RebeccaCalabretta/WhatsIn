package de.syntax_institut.androidabschlussprojekt.data.repository

import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProductDao
import de.syntax_institut.androidabschlussprojekt.data.local.model.toProduct
import de.syntax_institut.androidabschlussprojekt.data.mapping.ProductTypeMapper
import de.syntax_institut.androidabschlussprojekt.data.remote.api.BeautyApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.api.FoodApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.toProduct
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.helper.ProductType
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.model.toScannedProduct
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class DefaultProductRepository(
    private val foodApi: FoodApiService,
    private val beautyApi: BeautyApiService,
    private val dao: ScannedProductDao
) : ProductRepository {

    override suspend fun fetchProductByBarcode(barcode: String): Product {

        val foodResult = tryLoadProduct(barcode, foodApi, ProductType.FOOD)
        if (foodResult != null) return foodResult

        val beautyResult = tryLoadProduct(barcode, beautyApi, ProductType.BEAUTY)
        if (beautyResult != null) return beautyResult

        throw ProductException(ProductError.NOT_FOUND)
    }

    private suspend fun tryLoadProduct(
        barcode: String,
        api: Any,
        type: ProductType
    ): Product? {
        return try {
            val response = when (api) {
                is FoodApiService -> api.getProductByBarcode(barcode)
                is BeautyApiService -> api.getProductByBarcode(barcode)
                else -> return null
            }

            val productDto = response.product
            if (productDto == null) {
                null
            } else {
                val finalType = ProductTypeMapper.determineProductType(
                    productDto.categoriesTags,
                    fallback = type,
                    brand = productDto.brand
                )

                val product = productDto.toProduct(finalType)

                return product
            }

        } catch (e: HttpException) {
            if (e.code() == 404) {
                null
            } else {
                throw ProductException(ProductError.NETWORK)
            }
        } catch (e: IOException) {
            throw ProductException(ProductError.NETWORK)
        } catch (e: Exception) {
            throw ProductException(ProductError.UNKNOWN)
        }
    }

    override suspend fun saveScannedProduct(product: Product) {
        try {
            val existing = dao.getByBarcode(product.barcode)

            val merged = product.copy(
                isFavorite = existing?.isFavorite ?: false,
                timestamp = System.currentTimeMillis()
            )

            dao.insert(merged.toScannedProduct())

        } catch (e: Exception) {
            throw ProductException(ProductError.DATABASE)
        }
    }

    override suspend fun getProductFromDatabase(barcode: String): Product {
        return dao.getByBarcode(barcode)?.toProduct()
            ?: throw ProductException(ProductError.NOT_FOUND)
    }

    override suspend fun getScannedProducts(): Flow<List<ScannedProduct>> {
        return dao.getAll()
    }

    override suspend fun updateFavorite(barcode: String, isFavorite: Boolean) {
        dao.updateFavorite(barcode, isFavorite)
    }

    override suspend fun deleteProduct(barcode: String) {
        dao.deleteByBarcode(barcode)
    }
}