package com.example.garden.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class LightLevel {
    Bright,
    Medium,
    Low,
    None
}

/**
 * A simple data class to represent a Plant
 */
@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    val id: Int = 0,

    @ColumnInfo
    val species: String,

    @ColumnInfo
    val synonyms: List<String>,

    @ColumnInfo
    val lightLevel: LightLevel,

    @ColumnInfo
    val needsDirectLight: Boolean,

    @ColumnInfo
    val isNative: Boolean,

    @ColumnInfo
    val notes: String
)
