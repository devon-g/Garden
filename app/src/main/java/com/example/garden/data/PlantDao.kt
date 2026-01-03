package com.example.garden.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Insert
    suspend fun insert(plant: Plant)

    @Update
    suspend fun update(plant: Plant)

    @Delete
    suspend fun delete(plant: Plant)

    @Query("SELECT * from plants WHERE id = :id")
    fun getPlant(id: Int): Flow<Plant>

    @Query("SELECT * from plants ORDER BY species ASC")
    fun getAllPlants(): Flow<List<Plant>>
}