package com.example.garden.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.garden.R
import com.example.garden.ui.viewmodel.GardenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardenScreen(
    onAddEntryClick: () -> Unit,
    onDetailClick: (Int) -> Unit,
    viewModel: GardenViewModel,
    modifier: Modifier = Modifier,
) {
    val gardenUiState by viewModel.gardenUiState.collectAsState()

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
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.garden_screen_title))
                },
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
        ) {
            items(gardenUiState.plants) { plant ->
                // TODO: Make a clickable card that navigates to a screen with more details
                Card(
                    modifier = Modifier.clickable {
                        onDetailClick(plant.id)
                    }
                ) {
                    Text(
                        text = plant.species
                    )
                }
            }
        }
    }
}