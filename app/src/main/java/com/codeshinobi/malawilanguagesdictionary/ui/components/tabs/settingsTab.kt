package com.codeshinobi.malawilanguagesdictionary.ui.components.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference

@Composable
fun settingsTab(
    navController: NavController,
    modifier: Modifier = Modifier,
    databaseReference: DatabaseReference
) {
    Text(text = "Settings", modifier = modifier)
}