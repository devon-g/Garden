package com.example.garden.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.garden.R
import com.example.garden.ui.GardenTopAppBar
import com.example.garden.ui.viewmodel.PlantDetailViewModel
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
        modifier = modifier,
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
    ) { }
}