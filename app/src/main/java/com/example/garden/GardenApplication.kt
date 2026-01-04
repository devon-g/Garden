package com.example.garden

import android.app.Application
import com.example.garden.data.AppContainer
import com.example.garden.data.AppDataContainer

/**
 * Application to contain
 */
class GardenApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}