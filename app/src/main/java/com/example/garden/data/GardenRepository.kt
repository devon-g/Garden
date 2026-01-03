package com.example.garden.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository to interact with plants from a garden datasource
 */
interface GardenRepository {
    fun getAllPlantsStream(): Flow<List<Plant>>
    fun getPlantStream(id: Int): Flow<Plant?>
    suspend fun insertPlant(plant: Plant)
    suspend fun deletePlant(plant: Plant)
    suspend fun updatePlant(plant: Plant)
}