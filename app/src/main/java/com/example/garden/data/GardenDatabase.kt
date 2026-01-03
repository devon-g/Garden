package com.example.garden.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Singleton database holder.
 */
@Database(entities = [Plant::class], version = 1, exportSchema = false)
abstract class GardenDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        @Volatile
        private var Instance: GardenDatabase? = null

        fun getDatabase(context: Context): GardenDatabase {
            // If an instance does not already exist, create a new one
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GardenDatabase::class.java, "garden_database")
                    .fallbackToDestructiveMigration(dropAllTables = false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}