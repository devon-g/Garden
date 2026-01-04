package com.example.garden.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import kotlin.enums.EnumEntries

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
                    onSaveClick()
                }
            },
            updateUiState = viewModel::updateUiState,
            modifier = modifier
                .padding(innerPadding)
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
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
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

        modifier = modifier.fillMaxWidth()
    ) {
        // Species Field
        TextField(
            value = plantDetails.species,
            onValueChange = { speciesValue ->
                updateUiState(plantDetails.copy(species = speciesValue))
            },
            label = { Text(text = stringResource(R.string.species_field)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        // Light Level Field
        EnumDropdownMenu(
            labelResource = R.string.light_needed_field,
            selected = plantDetails.lightLevel,
            entries = LightLevel.entries,
            onValueChange = { level: LightLevel ->
                updateUiState(plantDetails.copy(lightLevel = level))
            },
        )
        // Direct Light Field
        LabelSwitch(
            labelResource = R.string.direct_light_field,
            checked = plantDetails.needsDirectLight,
            onCheckedChange = { checked ->
                updateUiState(plantDetails.copy(needsDirectLight = checked))
            }
        )
        // Direct Light Field
        LabelSwitch(
            labelResource = R.string.native_plant_field,
            checked = plantDetails.isNative,
            onCheckedChange = { checked ->
                updateUiState(plantDetails.copy(isNative = checked))
            }
        )
        // Notes Field
        TextField(
            value = plantDetails.notes,
            onValueChange = { note ->
                updateUiState(plantDetails.copy(notes = note))
            },
            label = { Text(text = stringResource(R.string.notes_field)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LabelSwitch(
    @StringRes labelResource: Int,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(text = stringResource(labelResource))
        Spacer(modifier = Modifier.weight(1F))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun <E : Enum<E>>EnumDropdownMenu(
    @StringRes labelResource: Int,
    selected: E,
    entries: EnumEntries<E>,
    onValueChange: (E) -> Unit,
    modifier: Modifier = Modifier
) {
    // Local state so that the drop down knows when to appear and what was selected
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        TextField(
            value = selected.name,
            onValueChange = {},
            label = {
                Text(text = stringResource(labelResource))
            },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            entries.forEach { selection ->
                DropdownMenuItem(
                    text = { Text(text = selection.name) },
                    onClick = {
                        onValueChange(selection)
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