package de.syntax_institut.androidabschlussprojekt.data.repository

import android.util.Log
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProductDao
import de.syntax_institut.androidabschlussprojekt.data.remote.ProductApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.model.toProduct
import de.syntax_institut.androidabschlussprojekt.error.ProductError
import de.syntax_institut.androidabschlussprojekt.error.ProductException
import de.syntax_institut.androidabschlussprojekt.model.Product
import de.syntax_institut.androidabschlussprojekt.model.toScannedProduct
import retrofit2.HttpException


class DefaultProductRepository(
    private val api: ProductApiService,
    private val scannedProductDao: ScannedProductDao
) : ProductRepository {

    override suspend fun fetchProductByBarcode(barcode: String): Product {
        try {
            Log.d("ProductRepository", "Starte API-Abruf für Barcode: $barcode")

            val response = api.getProductByBarcode(barcode)
            val productDto = response.product

            if (productDto == null) {
                Log.w("ProductRepository", "Produkt nicht gefunden (null Response)")
                throw ProductException(ProductError.NOT_FOUND)
            }

            val product = productDto.toProduct()
            Log.d("ProductRepository", "Produkt erfolgreich geladen: ${product.name}")
            return product

        } catch (e: HttpException) {
            if (e.code() == 404) {
                Log.w("ProductRepository", "Produkt nicht gefunden (404)")
                throw ProductException(ProductError.NOT_FOUND)
            } else {
                Log.e("ProductRepository", "Netzwerkfehler: ${e.code()}")
                throw ProductException(ProductError.NETWORK)
            }
        } catch (e: Exception) {
            Log.e("ProductRepository", "Unbekannter Fehler: ${e.message}")
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
}