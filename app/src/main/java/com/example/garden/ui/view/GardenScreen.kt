package com.example.garden.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.garden.ui.AppViewModelProvider
import com.example.garden.ui.theme.GardenTheme
import com.example.garden.ui.viewmodel.GardenViewModel

@Composable
fun GardenScreen(
    modifier: Modifier = Modifier,
    viewModel: GardenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val gardenUiState = viewModel.gardenUiState
    LazyColumn(modifier) {
        items(gardenUiState.value.plants) {
            // TODO: Make a clickable card that navigates to a screen with more details
            Text(text = it.species)
        }
    }

    // TODO: FloatingActionButton to navigate to a screen to add a new plant entry
}

@Preview(showBackground = true)
@Composable
fun GardenScreenPreview() {
    GardenTheme {
        GardenScreen()
    }
}
