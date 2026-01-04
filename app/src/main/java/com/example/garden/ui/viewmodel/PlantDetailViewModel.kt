package com.example.garden.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden.data.GardenRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlantDetailViewModel(
    private val plantId: Int,
    private val gardenRepository: GardenRepository
) : ViewModel() {
    /**
     * Current UI State watching for changes from data source
     */
    val plantUiState: StateFlow<PlantUiState> = gardenRepository.getPlantStream(plantId)
        .filterNotNull()
        .map { it.toPlantUiState(isEntryValid = true) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = PlantUiState()
        )

    suspend fun deletePlant() {
        if (plantUiState.value.plantDetails.species.isNotBlank()) {
            gardenRepository.deletePlant(plantUiState.value.plantDetails.toPlant())
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}