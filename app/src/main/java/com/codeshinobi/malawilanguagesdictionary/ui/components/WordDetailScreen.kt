package com.codeshinobi.malawilanguagesdictionary.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codeshinobi.malawilanguagesdictionary.ui.components.tabs.Word

@Composable
fun WordDetailScreen(navController: NavController, word: Word) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Word: ${word.word}", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Meaning: ${word.meaning}", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Example: ${word.example}", fontSize = 18.sp)
    }
}