import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codeshinobi.malawilanguagesdictionary.ui.components.tabs.Word
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordOfTheDay(
    navController: NavController,
    word: Word
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            val jsonString = Gson().toJson(word)
            navController.navigate("wordDetail/$jsonString")
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(100.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = word.word,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = word.meaning,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )

        }
    }
}
