package com.codeshinobi.malawilanguagesdictionary.ui.components.tabs

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

@Composable
fun homeTab(navController: NavController, modifier: Modifier, databaseReference: DatabaseReference) {
    var wordOfTheDay by remember { mutableStateOf<Word?>(null) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var searchResults by remember { mutableStateOf<List<Word>>(emptyList()) }

    LaunchedEffect(Unit) {
        wordOfTheDay = getRandomWord(databaseReference)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        wordOfTheDay?.let { word ->
            Text(text = "Word of the Day: ${word.word}")
            Text(text = "Meaning: ${word.meaning}")
            Text(text = "Example: ${word.example}")
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                searchDatabase(databaseReference, searchQuery.text) { results ->
                    searchResults = results
                }
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        searchResults.forEach { word ->
            Text(text = word.word)
            Text(text = word.meaning)
            Text(text = word.example)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

suspend fun getRandomWord(databaseReference: DatabaseReference): Word? {
    val languages =listOf("chichewa","lomwe","tumbuka")
    val language = languages[Random.nextInt(languages.size)]
    return try {
        val snapshot = databaseReference.child("languages").child(language).child("words").get().await()
        val words = snapshot.children.mapNotNull { it.getValue(Word::class.java) }
        if (words.isNotEmpty()) {
            words[Random.nextInt(words.size)]
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }
}
data class Word(
    val word: String = "",
    val meaning: String = "",
    val example: String = ""
)
data class Language(
    val words: List<Word> = emptyList()
)
fun searchDatabase(databaseReference: DatabaseReference, query: String, onResult: (List<Word>) -> Unit) {
    databaseReference.orderByChild("word").startAt(query).endAt("$query\uf8ff")
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val results = snapshot.children.mapNotNull { it.getValue(Word::class.java) }
                onResult(results)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
}