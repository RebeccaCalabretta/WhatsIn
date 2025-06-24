package de.syntax_institut.androidabschlussprojekt.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterListResponse
import de.syntax_institut.androidabschlussprojekt.data.remote.model.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://world.openfoodfacts.org/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("product/{barcode}")
    suspend fun getProductByBarcode(@Path("barcode") barcode: String): ProductResponse

    @GET("labels.json")
    suspend fun getLabels(): FilterListResponse

    @GET("additives.json")
    suspend fun getAdditives(): FilterListResponse

    @GET("allergens.json")
    suspend fun getAllergens(): FilterListResponse

    @GET("countries.json")
    suspend fun getCountries(): FilterListResponse

    @GET("brands.json")
    suspend fun getBrands(): FilterListResponse
}

object WhatsInAPI {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
