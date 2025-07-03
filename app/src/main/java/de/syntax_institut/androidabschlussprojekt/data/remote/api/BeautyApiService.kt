package de.syntax_institut.androidabschlussprojekt.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.androidabschlussprojekt.data.remote.model.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL_BEAUTY = "https://world.openbeautyfacts.org/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitBeauty = Retrofit.Builder()
    .baseUrl(BASE_URL_BEAUTY)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface BeautyApiService {
    @GET("product/{barcode}")
    suspend fun getProductByBarcode(@Path("barcode") barcode: String) : ProductResponse
}

object BeautyApi {
    val service: BeautyApiService by lazy {
        retrofitBeauty.create(BeautyApiService::class.java)
    }
}