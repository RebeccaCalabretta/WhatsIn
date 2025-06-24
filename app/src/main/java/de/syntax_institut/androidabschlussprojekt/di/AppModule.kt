package de.syntax_institut.androidabschlussprojekt.di

import de.syntax_institut.androidabschlussprojekt.data.remote.WhatsInAPI
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultFilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.FilterRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.viewmodel.ProductViewModel
import de.syntax_institut.androidabschlussprojekt.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single {
        WhatsInAPI.retrofitService
    }

    single<ProductRepository> {
        DefaultProductRepository(api = get())
    }

    single<FilterRepository> {
        DefaultFilterRepository(api = get())
    }

    viewModelOf(::ProductViewModel)
    viewModelOf(::SettingsViewModel)
}