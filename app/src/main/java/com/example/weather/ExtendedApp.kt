package com.example.weather

import android.app.Application
import com.example.weather.di.mainModule
import com.example.weather.di.mapperModule
import com.example.weather.di.repositoryModule
import com.example.weather.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ExtendedApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ExtendedApp)
            modules(
                mainModule,
                viewModelModule,
                repositoryModule,
                mapperModule
            )
        }
    }
}