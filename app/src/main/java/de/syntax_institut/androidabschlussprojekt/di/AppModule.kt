package de.syntax_institut.androidabschlussprojekt.di

import de.syntax_institut.androidabschlussprojekt.data.local.AppDatabase
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProductDao
import de.syntax_institut.androidabschlussprojekt.data.remote.api.FilterApi
import de.syntax_institut.androidabschlussprojekt.data.remote.api.FoodFilterApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.api.FoodApi
import de.syntax_institut.androidabschlussprojekt.data.remote.api.FoodApiService
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultFilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<FoodApiService> {
        FoodApi.service
    }

    single<FoodFilterApiService> {
        FilterApi.service
    }


    single<AppDatabase> {
        AppDatabase.getDatabase(androidContext())
    }

    single<ScannedProductDao> {
        get<AppDatabase>().scannedProductDao()
    }

    single<ProductRepository> {
        DefaultProductRepository(
            api = get(),
            scannedProductDao = get()
        )
    }

    single<FilterRepository> {
        DefaultFilterRepository(api = get())
    }

    viewModelOf(::ProductViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::ScanViewModel)
}