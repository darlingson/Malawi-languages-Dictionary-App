package com.codeshinobi.malawilanguagesdictionary.ui.components.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun settingsTab(
    navController: androidx.navigation.NavController,
    modifier: Modifier = Modifier
) {
    Text(text = "Settings", modifier = modifier)
}