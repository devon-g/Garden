package com.example.garden.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.garden.R
import com.example.garden.ui.theme.GardenTheme
import com.example.garden.ui.viewmodel.PlantDetails
import com.example.garden.ui.viewmodel.PlantEntryViewModel
import com.example.garden.ui.viewmodel.PlantUiState
import kotlinx.coroutines.launch

@Composable
fun PlantEntryScreen(
    viewModel: PlantEntryViewModel,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val plantUiState by viewModel.plantUiState.collectAsState()
    Scaffold(modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            PlantEntryBody(
                plantUiState = plantUiState,
                onClick = {
                    coroutineScope.launch {
                        viewModel.savePlant()
                    }
                    onSaveClick()
                },
                onValueChange = viewModel::updateUiState
            )
        }
    }
}

@Composable
fun PlantEntryBody(
    plantUiState: PlantUiState,
    onClick: () -> Unit,
    onValueChange: (PlantDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    PlantDetailForm(
        plantDetails = plantUiState.plantDetails,
        onValueChange = onValueChange,
        modifier = modifier
    )
    Button(
        onClick = onClick
    ) {
        Text(text = stringResource(R.string.save))
    }
}

@Composable
fun PlantDetailForm(
    plantDetails: PlantDetails,
    onValueChange: (PlantDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        TextField(
            value = plantDetails.species,
            onValueChange = { speciesValue ->
                onValueChange(plantDetails.copy(species = speciesValue))
            },
            label = { Text(text = stringResource(R.string.species_field)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantEntryBody() {
    GardenTheme {
        PlantEntryBody(
            onClick = {},
            plantUiState = PlantUiState(),
            onValueChange = {}
        )
    }
}