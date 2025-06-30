package de.syntax_institut.androidabschlussprojekt.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterListResponse
import de.syntax_institut.androidabschlussprojekt.data.remote.model.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL_BEAUTY = "https://world.openbeautyfacts.org/api/v2/"
private const val BASE_URL_BEAUTY_FILTER = "https://world.openbeautyfacts.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitBeauty = Retrofit.Builder()
    .baseUrl(BASE_URL_BEAUTY)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

private val retrofitBeautyFilter = Retrofit.Builder()
    .baseUrl(BASE_URL_BEAUTY_FILTER)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface BeautyApiService {
    @GET("product/{barcode}")
    suspend fun getProductByBarcode(@Path("barcode") barcode: String) : ProductResponse
}

interface BeautyFilterApiService {
    @GET("brands.json")
    suspend fun getBrands(): FilterListResponse

    @GET("additives.json")
    suspend fun getAdditives(): FilterListResponse

    @GET("labels.json")
    suspend fun getLabels(): FilterListResponse

    @GET("allergens.json")
    suspend fun getAllergens(): FilterListResponse

    @GET("countries.json")
    suspend fun getCountries(): FilterListResponse
}

object BeautyApi {
    val service: BeautyApiService by lazy {
        retrofitBeauty.create(BeautyApiService::class.java)
    }
}

object BeautyFilterApi {
    val service: BeautyFilterApiService by lazy {
        retrofitBeautyFilter.create(BeautyFilterApiService::class.java)
    }
}