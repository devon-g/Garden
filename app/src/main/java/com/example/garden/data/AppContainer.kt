package com.example.garden.data

import android.content.Context

interface AppContainer {
    val gardenRepository: GardenRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val gardenRepository: GardenRepository by lazy {
        OfflineGardenRepository(plantDao = GardenDatabase.getDatabase(context).plantDao())
    }
}