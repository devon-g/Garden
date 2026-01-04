package com.example.garden.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.garden.ui.theme.GardenTheme

@Composable
fun GardenApp(modifier: Modifier = Modifier) {
}

@Preview(showBackground = true)
@Composable
fun GardenAppPreview() {
    GardenTheme {
        GardenApp()
    }
}
