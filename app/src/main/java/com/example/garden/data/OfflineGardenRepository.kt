package com.example.garden.data

import kotlinx.coroutines.flow.Flow

/**
 * Offline repository that interacts with the local garden database through DAOs.
 */
class OfflineGardenRepository(private val plantDao: PlantDao) : GardenRepository {
    override fun getAllPlantsStream(): Flow<List<Plant>> = plantDao.getAllPlants()
    override fun getPlantStream(id: Int): Flow<Plant?> = plantDao.getPlant(id)
    override suspend fun insertPlant(plant: Plant) = plantDao.insert(plant)
    override suspend fun deletePlant(plant: Plant) = plantDao.delete(plant)
    override suspend fun updatePlant(plant: Plant) = plantDao.update(plant)
}