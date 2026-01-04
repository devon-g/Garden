package com.example.garden.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.garden.R
import com.example.garden.ui.viewmodel.GardenViewModel

@Composable
fun GardenScreen(
    onAddEntryClick: () -> Unit,
    viewModel: GardenViewModel,
    modifier: Modifier = Modifier,
) {
    val gardenUiState = viewModel.gardenUiState

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddEntryClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_new_plant_entry)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(gardenUiState.value.plants) {
                // TODO: Make a clickable card that navigates to a screen with more details
                Text(text = it.species)
            }
        }
    }
}