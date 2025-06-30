package de.syntax_institut.androidabschlussprojekt.data.remote.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.syntax_institut.androidabschlussprojekt.data.remote.model.FilterListResponse
import de.syntax_institut.androidabschlussprojekt.data.remote.model.ProductResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL_FOOD = "https://world.openfoodfacts.org/api/v2/"
private const val BASE_URL_FOOD_FILTER = "https://world.openfoodfacts.org/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitFood = Retrofit.Builder()
    .baseUrl(BASE_URL_FOOD)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

private val retrofitFoodFilter = Retrofit.Builder()
    .baseUrl(BASE_URL_FOOD_FILTER)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface FoodApiService {
    @GET("product/{barcode}")
    suspend fun getProductByBarcode(@Path("barcode") barcode: String): ProductResponse
}

interface FoodFilterApiService {
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


object FoodApi {
    val service: FoodApiService by lazy {
        retrofitFood.create(FoodApiService::class.java)
    }
}

object FilterApi {
    val service: FoodFilterApiService by lazy {
        retrofitFoodFilter.create(FoodFilterApiService::class.java)
    }
}