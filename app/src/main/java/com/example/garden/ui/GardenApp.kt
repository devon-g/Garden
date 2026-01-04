package com.example.garden.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.garden.GardenApplication
import com.example.garden.ui.screen.GardenScreen
import com.example.garden.ui.screen.PlantDetailScreen
import com.example.garden.ui.screen.PlantEditScreen
import com.example.garden.ui.screen.PlantEntryScreen
import com.example.garden.ui.viewmodel.GardenViewModel
import com.example.garden.ui.viewmodel.PlantDetailViewModel
import com.example.garden.ui.viewmodel.PlantEditViewModel
import com.example.garden.ui.viewmodel.PlantEntryViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Garden : NavKey

@Serializable
data object PlantEntry : NavKey

@Serializable
data class PlantDetails(val id: Int) : NavKey

@Serializable
data class PlantEdit(val id: Int) : NavKey

@Composable
fun GardenApp(
    modifier: Modifier = Modifier
) {
    // TODO: List plants in garden
    //       Add entry to garden list
    //       View details of garden entry

    // Back stack that survives recomposition and configuration changes
    val backStack = rememberNavBackStack(Garden)
    // TODO: Getting the application context inside the factory was giving null pointer.
    //       figure out why
    val application = LocalContext.current.applicationContext as GardenApplication
    val gardenRepository = application.container.gardenRepository

    NavDisplay(
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Garden> {
                val viewModel: GardenViewModel =
                    viewModel(factory = GardenViewModelFactory(gardenRepository))
                GardenScreen(
                    onAddEntryClick = { backStack.add(PlantEntry) },
                    onDetailClick = { id ->
                        backStack.add(PlantDetails(id))
                    },
                    viewModel
                )
            }
            entry<PlantEntry> {
                val viewModel: PlantEntryViewModel =
                    viewModel(factory = PlantEntryViewModelFactory(gardenRepository))
                PlantEntryScreen(
                    viewModel,
                    navigateBack = { backStack.removeLastOrNull() },
                )
            }
            entry<PlantEdit> { key ->
                val viewModel: PlantEditViewModel =
                    viewModel(factory = PlantEditViewModelFactory(key.id, gardenRepository))
                PlantEditScreen(
                    viewModel,
                    navigateBack = { backStack.removeLastOrNull() }
                )
            }
            entry<PlantDetails> { key ->
                val viewModel: PlantDetailViewModel =
                    viewModel(factory = PlantDetailViewModelFactory(key.id, gardenRepository))
                PlantDetailScreen(
                    viewModel,
                    navigateBack = { backStack.removeLastOrNull() },
                    onEditEntryClick = { id ->
                        backStack.add(PlantEdit(id))
                    },
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardenTopAppBar(
    @StringRes titleResource: Int,
    canGoBack: Boolean,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(titleResource))
        },
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = onBackClick ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = modifier
    )
}