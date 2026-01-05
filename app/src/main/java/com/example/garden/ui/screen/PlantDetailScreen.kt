package com.example.garden.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.garden.R
import com.example.garden.ui.GardenTopAppBar
import com.example.garden.ui.theme.GardenTheme
import com.example.garden.ui.viewmodel.PlantDetailViewModel
import com.example.garden.ui.viewmodel.PlantDetails
import kotlinx.coroutines.launch

@Composable
fun PlantDetailScreen(
    viewModel: PlantDetailViewModel,
    onEditEntryClick: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val plantUiState by viewModel.plantUiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            Column() {
                FloatingActionButton(
                    onClick = {
                        onEditEntryClick(plantUiState.plantDetails.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.add_new_plant_entry)
                    )
                }
                Spacer(Modifier.padding(16.dp))
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.deletePlant()
                            navigateBack()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_plant_entry)
                    )
                }
            }
        },
        topBar = {
            GardenTopAppBar(
                titleResource = R.string.plant_detail_screen_title,
                canGoBack = true,
                onBackClick = navigateBack
            )
        },
    ) { innerPadding ->
        PlantDetailBody(
            plantDetails = plantUiState.plantDetails,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PlantDetailBody(
    plantDetails: PlantDetails,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Card(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        plantDetails.species,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null,
                        modifier = Modifier.size(250.dp)
                    )
                }
            }
            Card(modifier = Modifier
                .weight(1f)
                .fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Basic show info
                    Text(plantDetails.synonym)
                    Text("Light: ${plantDetails.lightLevel.name}")
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Direct Light")
                        Icon(
                            imageVector =
                                if (plantDetails.needsDirectLight) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Native Plant")
                        Icon(
                            imageVector =
                                if (plantDetails.isNative) Icons.Default.Check else Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        }
        Card(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
        ) {
            Text(
                text = plantDetails.notes,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantDetailBody() {
    GardenTheme {
        PlantDetailBody(
            PlantDetails(
                species = "Test Species",
                synonym = "Test thing",
                notes = "this is such a wonderful note i'm so glad i got to add this into my project and uh oh it's going past the line column... soon i ont be able to see where i'm typing anymore.\nWater 8 times per day"
            ),
        )
    }
}