package com.example.garden.di

import com.example.garden.data.GardenDatabase
import com.example.garden.data.GardenRepository
import com.example.garden.data.OfflineGardenRepository
import com.example.garden.data.PlantDao
import com.example.garden.ui.viewmodel.GardenViewModel
import com.example.garden.ui.viewmodel.PlantDetailViewModel
import com.example.garden.ui.viewmodel.PlantEditViewModel
import com.example.garden.ui.viewmodel.PlantEntryViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val dataModule = module {
    single<GardenDatabase> { GardenDatabase.getDatabase(androidApplication()) }
    single<PlantDao> { get<GardenDatabase>().plantDao() }
    single<GardenRepository> { OfflineGardenRepository(get()) }
}

val viewModelModule = module {
    viewModelOf(::GardenViewModel)
    viewModelOf(::PlantDetailViewModel)
    viewModelOf(::PlantEditViewModel)
    viewModelOf(::PlantEntryViewModel)
}