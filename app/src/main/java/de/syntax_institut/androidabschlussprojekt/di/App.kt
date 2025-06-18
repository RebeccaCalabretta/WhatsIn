package de.syntax_institut.androidabschlussprojekt.di

import android.app.Application
import android.util.Log
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("KoinCheck", "Koin wird gestartet...")

       startKoin {
            androidContext(this@App)
            modules(appModule)
        }
        Log.d("AppStart", "Koin wurde gestartet")

    }
}

