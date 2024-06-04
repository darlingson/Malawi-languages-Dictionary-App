package com.codeshinobi.malawilanguagesdictionary.ui.components.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference

@Composable
fun dictionariesTab(
    navController: NavController,
    modifier: Modifier = Modifier,
    databaseReference: DatabaseReference
) {
    Column {
        Text("Dictionaries")
        Text("Chichewa")
        Text("Lomwe")
        Text("Tumbuka")
    }
}