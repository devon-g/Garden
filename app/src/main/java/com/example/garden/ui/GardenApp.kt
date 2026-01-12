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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.garden.ui.screen.GardenScreen
import com.example.garden.ui.screen.PlantDetailScreen
import com.example.garden.ui.screen.PlantEditScreen
import com.example.garden.ui.screen.PlantEntryScreen
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Serializable
data object Garden : NavKey

@Serializable
data object PlantEntry : NavKey

@Serializable
data class PlantDetail(val id: Int) : NavKey

@Serializable
data class PlantEdit(val id: Int) : NavKey

@Composable
fun GardenApp(
    modifier: Modifier = Modifier
) {
    // Back stack that survives recomposition and configuration changes
    val backStack = rememberNavBackStack(Garden)

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
                GardenScreen(
                    onAddEntryClick = { backStack.add(PlantEntry) },
                    onDetailClick = { id ->
                        backStack.add(PlantDetail(id))
                    },
                    viewModel = koinViewModel()
                )
            }
            entry<PlantEntry> {
                PlantEntryScreen(
                    koinViewModel(),
                    navigateBack = { backStack.removeLastOrNull() },
                )
            }
            entry<PlantEdit> { key ->
                PlantEditScreen(
                    viewModel = koinViewModel { parametersOf(key.id) },
                    navigateBack = { backStack.removeLastOrNull() }
                )
            }
            entry<PlantDetail> { key ->
                PlantDetailScreen(
                    viewModel = koinViewModel { parametersOf(key.id) },
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
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(titleResource))
        },
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = onBackClick) {
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