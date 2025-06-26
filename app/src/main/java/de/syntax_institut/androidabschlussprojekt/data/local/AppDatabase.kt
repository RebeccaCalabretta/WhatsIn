package de.syntax_institut.androidabschlussprojekt.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProduct
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProductDao


@Database(entities = [ScannedProduct::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scannedProductDao(): ScannedProductDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "scanned_product_database")
                    .build().also { Instance = it }
            }
        }
    }
}