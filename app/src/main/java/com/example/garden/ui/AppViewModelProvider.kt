package com.example.garden.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.garden.data.GardenRepository
import com.example.garden.ui.viewmodel.GardenViewModel
import com.example.garden.ui.viewmodel.PlantDetailViewModel
import com.example.garden.ui.viewmodel.PlantEditViewModel
import com.example.garden.ui.viewmodel.PlantEntryViewModel

class GardenViewModelFactory(
    private val gardenRepository: GardenRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GardenViewModel(gardenRepository) as T
    }
}

class PlantDetailViewModelFactory(
    private val plantId: Int,
    private val gardenRepository: GardenRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlantDetailViewModel(plantId, gardenRepository) as T
    }
}

class PlantEntryViewModelFactory(
    private val gardenRepository: GardenRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlantEntryViewModel(gardenRepository) as T
    }
}

class PlantEditViewModelFactory(
    private val plantId: Int,
    private val gardenRepository: GardenRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlantEditViewModel(plantId, gardenRepository) as T
    }
}