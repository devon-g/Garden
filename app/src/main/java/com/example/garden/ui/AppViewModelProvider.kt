package com.example.garden.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.garden.GardenApplication
import com.example.garden.ui.viewmodel.GardenViewModel
import com.example.garden.ui.viewmodel.PlantEntryViewModel

class AppViewModelProvider(private val application: Application) {
    val factory: ViewModelProvider.Factory = viewModelFactory {
        // TODO: Provide view models with access to the application's repository
        initializer {
            GardenViewModel(
                (application as GardenApplication).container.gardenRepository
            )
        }
        initializer {
            PlantEntryViewModel(
                (application as GardenApplication).container.gardenRepository
            )
        }
    }
}