package de.syntax_institut.androidabschlussprojekt.di

import de.syntax_institut.androidabschlussprojekt.data.local.AppDatabase
import de.syntax_institut.androidabschlussprojekt.data.local.model.ScannedProductDao
import de.syntax_institut.androidabschlussprojekt.data.remote.api.BeautyApi
import de.syntax_institut.androidabschlussprojekt.data.remote.api.BeautyApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.api.FoodApi
import de.syntax_institut.androidabschlussprojekt.data.remote.api.FoodApiService
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultFilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterCheckUseCase
import de.syntax_institut.androidabschlussprojekt.domain.usecase.FilterConfigUseCase
import de.syntax_institut.androidabschlussprojekt.domain.usecase.SearchUseCase
import de.syntax_institut.androidabschlussprojekt.viewmodel.CollectionViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ScanViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    single<FoodApiService> {
        FoodApi.service
    }

    single<BeautyApiService> {
        BeautyApi.service
    }

    single<AppDatabase> {
        AppDatabase.getDatabase(androidContext())
    }

    single<ScannedProductDao> {
        get<AppDatabase>().scannedProductDao()
    }

    singleOf(::DefaultProductRepository) bind ProductRepository::class
    singleOf(::DefaultFilterRepository) bind FilterRepository::class


    factoryOf(::FilterCheckUseCase)
    factoryOf(::FilterConfigUseCase)
    factoryOf(::SearchUseCase)

    viewModelOf(::ProductViewModel)
    viewModelOf(::FilterViewModel)
    viewModelOf(::ScanViewModel)
    viewModelOf(::CollectionViewModel)
    viewModelOf(::SettingsViewModel)
}