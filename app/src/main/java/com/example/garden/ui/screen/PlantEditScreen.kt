package com.example.garden.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.garden.ui.viewmodel.PlantEditViewModel

@Composable
fun PlantEditScreen(
    viewModel: PlantEditViewModel,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val plantUiState by viewModel.plantUiState.collectAsState()
}
