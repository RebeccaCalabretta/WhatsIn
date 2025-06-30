package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProductDao
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
    private val scannedProductDao: ScannedProductDao
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
                Log.w("ProductRepository", "Produkt war null (${type.name})")
                null
            } else {
                val product = productDto.toProduct(type)
                Log.d("ProductRepository", "Produkt geladen über ${type.name}: ${product.name}")
                product
            }

        } catch (e: HttpException) {
            if (e.code() == 404) {
                Log.w("ProductRepository", "${type.name} API: Produkt nicht gefunden (404)")
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
            Log.d("ProductRepository", "Speichere Produkt mit Barcode: ${product.barcode}")
            scannedProductDao.insert(product.toScannedProduct())
            Log.d("ProductRepository", "Produkt gespeichert")
        } catch (e: Exception) {
            Log.e("ProductRepository", "Fehler beim Speichern: ${e.message}")
            throw ProductException(ProductError.DATABASE)
        }
    }

    override suspend fun getScannedProducts(): Flow<List<ScannedProduct>> {
        return scannedProductDao.getAll()
    }
}