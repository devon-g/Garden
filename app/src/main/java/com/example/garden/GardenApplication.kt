package com.example.garden

import android.app.Application
import com.example.garden.data.AppContainer
import com.example.garden.data.AppDataContainer
import com.example.garden.ui.AppViewModelProvider

/**
 * Application to contain
 */
class GardenApplication : Application() {
    lateinit var container: AppContainer
    lateinit var viewModelProvider: AppViewModelProvider
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        viewModelProvider = AppViewModelProvider(this)
    }
}