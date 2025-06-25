package de.syntax_institut.androidabschlussprojekt.di

import de.syntax_institut.androidabschlussprojekt.data.remote.FilterApi
import de.syntax_institut.androidabschlussprojekt.data.remote.FilterApiService
import de.syntax_institut.androidabschlussprojekt.data.remote.ProductApi
import de.syntax_institut.androidabschlussprojekt.data.remote.ProductApiService
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultFilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.viewmodel.FilterViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single<ProductApiService> {
        ProductApi.service
    }

    single<FilterApiService> {
        FilterApi.service
    }

    single<ProductRepository> {
        DefaultProductRepository(api = get())
    }

    single<FilterRepository> {
        DefaultFilterRepository(api = get())
    }

    viewModelOf(::ProductViewModel)
    viewModelOf(::FilterViewModel)
}