package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
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

class DefaultProductRepository(
    private val foodApi: FoodApiService,
    private val beautyApi: BeautyApiService,
    private val dao: ScannedProductDao
) : ProductRepository {

    override suspend fun fetchProductByBarcode(barcode: String): Product {
        Log.d("ProductRepository", "fetchProductByBarcode() aufgerufen mit Barcode: $barcode")

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
            Log.d("ProductRepository", "Versuche Barcode $barcode über ${type.name}")

            val response = when (api) {
                is FoodApiService -> api.getProductByBarcode(barcode)
                is BeautyApiService -> api.getProductByBarcode(barcode)
                else -> return null
            }

            val productDto = response.product
            if (productDto == null) {
                Log.w("ProductRepository", "Produkt war null (${type.name})")
                null
            } else {
                val finalType = ProductTypeMapper.determineProductType(
                    productDto.categoriesTags,
                    fallback = type,
                    brand = productDto.brand
                )

                val product = productDto.toProduct(finalType)
                Log.d(
                    "ProductRepository",
                    "Produkt geladen über ${finalType.name}: ${product.name}"
                )
                product
            }

        } catch (e: HttpException) {
            if (e.code() == 404) {
                Log.e("ProductRepository", "${type.name} API: Produkt nicht gefunden (404)")
                null
            } else {
                Log.e("ProductRepository", "${type.name} API: Netzwerkfehler ${e.code()}")
                throw ProductException(ProductError.NETWORK)
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "${type.name} API: Unbekannter Fehler: ${e.message}")
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

            Log.d(
                "ProductRepository",
                "Speichere ${merged.name} mit Favorit=${merged.isFavorite} und Timestamp=${merged.timestamp}"
            )

            dao.insert(merged.toScannedProduct())

        } catch (e: Exception) {
            Log.e("ProductRepository", "Fehler beim Speichern: ${e.message}")
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