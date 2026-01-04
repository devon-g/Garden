package com.example.garden.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.garden.GardenApplication
import com.example.garden.ui.viewmodel.GardenViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        // TODO: Provide view models with access to the application's repository
        initializer {
            GardenViewModel(
                gardenApplication().container.gardenRepository
            )
        }
    }
}

/**
 * Extension function to provide a handle to the application to retrieve dependencies
 */
fun CreationExtras.gardenApplication(): GardenApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GardenApplication)