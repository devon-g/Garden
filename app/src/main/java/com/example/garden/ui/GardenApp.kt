package com.example.garden.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.garden.GardenApplication
import com.example.garden.ui.screen.GardenScreen
import com.example.garden.ui.screen.PlantEntryScreen
import com.example.garden.ui.viewmodel.GardenViewModel
import com.example.garden.ui.viewmodel.PlantEntryViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Garden : NavKey

@Serializable
data object PlantEntry : NavKey

@Serializable
data class PlantDetails(val id: Int) : NavKey

@Composable
fun GardenApp(modifier: Modifier = Modifier) {
    // TODO: List plants in garden
    //       Add entry to garden list
    //       View details of garden entry

    // Back stack that survives recomposition and configuration changes
    val backStack = rememberNavBackStack(Garden)
    // TODO: Getting the application context inside the factory was giving null pointer.
    //       figure out why
    val application = LocalContext.current.applicationContext as GardenApplication

    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Garden> {
                val viewModel: GardenViewModel =
                    viewModel(factory = application.viewModelProvider.factory)
                GardenScreen(
                    onAddEntryClick = { backStack.add(PlantEntry) },
                    viewModel
                )
            }
            entry<PlantEntry> {
                val viewModel: PlantEntryViewModel =
                    viewModel(factory = application.viewModelProvider.factory)
                PlantEntryScreen(
                    viewModel,
                    onSaveClick = { backStack.remove(PlantEntry) },
                )
            }
            entry<PlantDetails> { key ->
                //PlantDetailsScreen()
            }
        }
    )
}