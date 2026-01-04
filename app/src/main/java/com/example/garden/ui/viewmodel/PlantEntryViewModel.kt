package com.example.garden.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.garden.data.GardenRepository
import com.example.garden.data.LightLevel
import com.example.garden.data.Plant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlantEntryViewModel(private val gardenRepository: GardenRepository) : ViewModel() {
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

    suspend fun savePlant() {
        if (_plantUiState.value.plantDetails.species.isNotBlank()) {
            gardenRepository.insertPlant(_plantUiState.value.plantDetails.toPlant())
        }
    }
}

data class PlantUiState(
    val plantDetails: PlantDetails = PlantDetails(),
    val isEntryValid: Boolean = false
)

data class PlantDetails(
    val id: Int = 0,
    val species: String = "",
    //val synonyms: List<String> = listOf(),
    val lightLevel: LightLevel = LightLevel.None,
    val needsDirectLight: Boolean = false,
    val isNative: Boolean = false,
    val notes: String = ""
)

/**
 * Convert PlantDetails to Plant Entity
 */
fun PlantDetails.toPlant(): Plant = Plant(
    id = id,
    species = species,
    //synonyms = synonyms,
    lightLevel = lightLevel,
    needsDirectLight = needsDirectLight,
    isNative = isNative,
    notes = notes
)