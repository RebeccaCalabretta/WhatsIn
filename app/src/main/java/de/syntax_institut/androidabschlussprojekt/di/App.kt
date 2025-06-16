package de.syntax_institut.androidabschlussprojekt.di

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@App)
            modules(appModule)
        }
    }
}