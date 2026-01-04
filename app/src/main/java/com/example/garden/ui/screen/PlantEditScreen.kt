package com.example.garden.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.garden.ui.viewmodel.PlantEditViewModel
import kotlinx.coroutines.launch

@Composable
fun PlantEditScreen(
    viewModel: PlantEditViewModel,
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
                    viewModel.updatePlant()
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
