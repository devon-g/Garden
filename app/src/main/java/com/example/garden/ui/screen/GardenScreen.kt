package com.example.garden.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.garden.R
import com.example.garden.ui.GardenTopAppBar
import com.example.garden.ui.theme.GardenTheme
import com.example.garden.ui.viewmodel.GardenViewModel
import com.example.garden.ui.viewmodel.PlantDetails

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
            GardenTopAppBar(
                titleResource = R.string.garden_screen_title,
                canGoBack = false
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            items(gardenUiState.plants) { plant ->
                // TODO: Make a clickable card that navigates to a screen with more details
                PlantCard(
                    plant = plant,
                    onDetailClick = onDetailClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun PlantCard(
    plant: PlantDetails,
    onDetailClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable {
            onDetailClick(plant.id)
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.List,
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Spacer(modifier = Modifier.weight(1F))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = plant.species,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.End
                )
                Text(
                    text = plant.synonym,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantCard() {
    GardenTheme {
        PlantCard(
            PlantDetails(
                species = "Test Species",
                synonym = "Test thing"
            ),
            onDetailClick = {},
        )
    }
}