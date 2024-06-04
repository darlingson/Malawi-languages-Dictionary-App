package com.codeshinobi.malawilanguagesdictionary.ui.components.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun dictionariesTab(
    navController: androidx.navigation.NavController,
    modifier: Modifier = Modifier
) {
    Column {
        Text("Dictionaries")
        Text("Chichewa")
        Text("Lomwe")
        Text("Tumbuka")
    }
}