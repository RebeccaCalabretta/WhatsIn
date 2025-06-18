package de.syntax_institut.androidabschlussprojekt.di

import de.syntax_institut.androidabschlussprojekt.data.remote.WhatsInAPI
import de.syntax_institut.androidabschlussprojekt.data.repository.DefaultProductRepository
import de.syntax_institut.androidabschlussprojekt.data.repository.ProductRepository
import de.syntax_institut.androidabschlussprojekt.viewModel.ProductViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val appModule = module {

    single {
        WhatsInAPI.retrofitService
    }

    single<ProductRepository> {
        DefaultProductRepository(api = get())
    }

    viewModelOf(::ProductViewModel)
}