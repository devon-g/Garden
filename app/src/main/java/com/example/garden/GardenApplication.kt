package com.example.garden

import android.app.Application
import com.example.garden.di.dataModule
import com.example.garden.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Application to contain
 */
class GardenApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GardenApplication)
            modules(
                dataModule,
                viewModelModule
            )
        }
    }
}