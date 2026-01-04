package com.example.garden.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.garden.R
import com.example.garden.data.LightLevel
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
        PlantEntryBody(
            plantUiState = plantUiState,
            onClick = {
                coroutineScope.launch {
                    viewModel.savePlant()
                }
                onSaveClick()
            },
            updateUiState = viewModel::updateUiState,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .safeContentPadding()
        )
    }
}

@Composable
fun PlantEntryBody(
    plantUiState: PlantUiState,
    onClick: () -> Unit,
    updateUiState: (PlantDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ) {
        PlantDetailForm(
            plantDetails = plantUiState.plantDetails,
            updateUiState = updateUiState,
            modifier = modifier
        )
        Button(
            onClick = onClick
        ) {
            Text(text = stringResource(R.string.save))
        }
    }
}

@Composable
fun PlantDetailForm(
    plantDetails: PlantDetails,
    updateUiState: (PlantDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        TextField(
            value = plantDetails.species,
            onValueChange = { speciesValue ->
                updateUiState(plantDetails.copy(species = speciesValue))
            },
            label = { Text(text = stringResource(R.string.species_field)) },
            singleLine = true
        )
        LightLevelDropDown(
            plantDetails = plantDetails,
            onValueChange = updateUiState
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Direct Light Needed")
            Switch(
                checked = plantDetails.needsDirectLight,
                onCheckedChange = { isChecked ->
                    updateUiState(plantDetails.copy(needsDirectLight = isChecked))
                }
            )
        }
    }
}

@Composable
fun LightLevelDropDown(
    plantDetails: PlantDetails,
    onValueChange: (PlantDetails) -> Unit,
    modifier: Modifier = Modifier
) {
    // Local state so that the drop down knows when to appear and what was selected
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(LightLevel.None) }

    Column(modifier = modifier) {
        TextField(
            value = selected.name,
            onValueChange = {},
            label = {
                Text(text = stringResource(R.string.light_needed_field))
            },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true,
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            LightLevel.entries.forEach { level ->
                DropdownMenuItem(
                    text = { Text(text = level.name) },
                    onClick = {
                        // Actually store the new value
                        onValueChange(plantDetails.copy(lightLevel = level))

                        // Just visual
                        selected = level
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlantEntryBody() {
    GardenTheme {
        PlantEntryBody(
            onClick = {},
            plantUiState = PlantUiState(),
            updateUiState = {}
        )
    }
}