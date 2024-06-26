package com.codeshinobi.malawilanguagesdictionary.ui.components.tabs

import WordOfTheDay
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codeshinobi.malawilanguagesdictionary.ui.components.SearchResultCard
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

@Composable
fun homeTab(navController: NavController, modifier: Modifier, databaseReference: DatabaseReference) {
    var wordOfTheDay by remember { mutableStateOf<Word?>(null) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var searchResults by remember { mutableStateOf(emptyList<Word>()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        isLoading = true
        wordOfTheDay = getRandomWord(databaseReference)
        isLoading = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery ->
                searchQuery = newQuery
                coroutineScope.launch {
                    isLoading = true
                    searchDatabase(databaseReference, searchQuery.text) { results ->
                        searchResults = results
                        isLoading = false
                    }
                }
            },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text(text = "Loading...")
        } else {
            if (searchResults.isNotEmpty() and searchQuery.text.isNotEmpty()) {
                Text(text = "Search Results")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp).verticalScroll(rememberScrollState())
                ) {
                    Text(text = "${searchResults.size} results")
                    searchResults.forEach { word ->
                        Spacer(modifier = Modifier.height(8.dp))
                        SearchResultCard(
                            navController = navController,
                            word = word,
                        )
                    }
                }
            } else {
                wordOfTheDay?.let { word ->
                    Text(text = "Word of the Day")
                    WordOfTheDay(navController, word)
                }
            }
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
            Log.d("getRandomWord", "No words found for language $language")
            words[Random.nextInt(words.size)]
        } else {
            Log.d("getRandomWord", "No words found for language $language")
            null
        }
    } catch (e: Exception) {
        Log.d("getRandomWord", "Error getting random word for language $language")
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
//suspend fun searchDatabase(databaseReference: DatabaseReference, query: String, onResult: (List<Word>) -> Unit) {
//    val language = listOf("chichewa", "lomwe", "tumbuka")[Random.nextInt(3)]
//    val wordsSnapshot = databaseReference.child("languages").child(language).child("words")
//        .orderByChild("word").equalTo(query).get().await()
//    Log.d("searchDatabase", "Found ${wordsSnapshot.childrenCount} words for language $language")
//    val words = wordsSnapshot.children.mapNotNull { it.getValue(Word::class.java) }
//    onResult(words)
//}
suspend fun searchDatabase(databaseReference: DatabaseReference, query: String, onResult: (List<Word>) -> Unit) {
    val languages = listOf("chichewa", "lomwe", "tumbuka")

    val allWords = mutableListOf<Word>()

    languages.forEach { language ->
        val wordsSnapshot = databaseReference
            .child("languages").child(language).child("words")
            .orderByChild("word")
            .startAt(query)
            .endAt(query + "\uf8ff")
            .get().await()

        val words = wordsSnapshot.children.mapNotNull { it.getValue(Word::class.java) }
        allWords.addAll(words)
    }

    onResult(allWords.take(10))
}
