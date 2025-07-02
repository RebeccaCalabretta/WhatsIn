package de.syntax_institut.androidabschlussprojekt.data.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScannedProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ScannedProduct)

    @Query("SELECT * FROM scanned_product ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ScannedProduct>>

    @Query("UPDATE scanned_product SET isFavorite = :isFavorite WHERE barcode = :barcode")
    suspend fun updateFavorite(barcode: String, isFavorite: Boolean)
}
