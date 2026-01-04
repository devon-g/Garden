package com.example.garden.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden.data.GardenRepository
import com.example.garden.data.Plant
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GardenViewModel(private val gardenRepository: GardenRepository) : ViewModel() {
    // Get plants from data repository by subscribing and converting Flow to StateFlow so that
    // the UI reacts when the list of items changes
    val gardenUiState: StateFlow<GardenUiState> = gardenRepository.getAllPlantsStream().map { GardenUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = GardenUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class GardenUiState(
    val plants: List<Plant> = listOf()
)