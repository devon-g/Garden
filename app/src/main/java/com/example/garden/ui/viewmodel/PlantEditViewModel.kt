package com.example.garden.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.garden.data.GardenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlantEditViewModel(private val gardenRepository: GardenRepository) : ViewModel() {
    /**
     * Current UI State
     */
    private val _plantUiState = MutableStateFlow(PlantUiState())
    val plantUiState: StateFlow<PlantUiState> = _plantUiState.asStateFlow()

    fun updateUiState(plantDetails: PlantDetails) {
        _plantUiState.update { uiState ->
            uiState.copy(
                plantDetails = plantDetails,
                isEntryValid = plantDetails.species.isNotBlank()
            )
        }
    }

    suspend fun updatePlant() {
        if (_plantUiState.value.plantDetails.species.isNotBlank()) {
            gardenRepository.updatePlant(_plantUiState.value.plantDetails.toPlant())
        }
    }
}
