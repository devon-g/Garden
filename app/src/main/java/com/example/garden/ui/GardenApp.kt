package com.example.garden.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.garden.ui.theme.GardenTheme
import com.example.garden.ui.view.GardenScreen
import com.example.garden.ui.viewmodel.GardenViewModel
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

    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Garden> {
                val viewModel: GardenViewModel = viewModel(factory = AppViewModelProvider.Factory)
                GardenScreen(viewModel)
            }
            entry<PlantEntry> {
                //PlantEntryScreen()
            }
            entry<PlantDetails> { key ->
                //PlantDetailsScreen()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GardenAppPreview() {
    GardenTheme {
        GardenApp()
    }
}
