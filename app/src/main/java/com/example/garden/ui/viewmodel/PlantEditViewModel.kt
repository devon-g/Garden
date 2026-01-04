package com.example.garden.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garden.data.GardenRepository
import com.example.garden.data.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlantEditViewModel(
    private val plantId: Int,
    private val gardenRepository: GardenRepository
) : ViewModel() {
    /**
     * Current UI State
     */
    private val _plantUiState = MutableStateFlow(PlantUiState())
    val plantUiState: StateFlow<PlantUiState> = _plantUiState.asStateFlow()

    /**
     * Get plantId information from repository using a coroutine to initialize state
     */
    init {
        viewModelScope.launch {
            _plantUiState.update {
                gardenRepository.getPlantStream(plantId)
                    .filterNotNull()
                    .first()
                    .toPlantUiState(true)
            }
        }
    }

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

fun Plant.toPlantUiState(isEntryValid: Boolean = false): PlantUiState = PlantUiState(
    plantDetails = this.toPlantDetails(),
    isEntryValid = isEntryValid
)

fun Plant.toPlantDetails(): PlantDetails = PlantDetails(
    id = id,
    species = species,
    //synonyms = synonyms,
    lightLevel = lightLevel,
    needsDirectLight = needsDirectLight,
    isNative = isNative,
    notes = notes
)