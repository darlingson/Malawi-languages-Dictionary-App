package com.codeshinobi.malawilanguagesdictionary.ui.components.tabs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference

@Composable
fun homeTab(navController: NavController, modifier: Modifier, databaseReference: DatabaseReference) {
    Text("Home")
}