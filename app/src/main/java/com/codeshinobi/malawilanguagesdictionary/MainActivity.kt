package com.codeshinobi.malawilanguagesdictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codeshinobi.malawilanguagesdictionary.ui.components.TabBarItem
import com.codeshinobi.malawilanguagesdictionary.ui.components.TabView
import com.codeshinobi.malawilanguagesdictionary.ui.components.tabs.homeTab
import com.codeshinobi.malawilanguagesdictionary.ui.theme.MalawiLanguagesDictionaryTheme
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeTab = TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
            val alertsTab = TabBarItem(title = "Alerts", selectedIcon = Icons.Filled.Notifications, unselectedIcon = Icons.Outlined.Notifications)
            val settingsTab = TabBarItem(title = "Settings", selectedIcon = Icons.Filled.Settings, unselectedIcon = Icons.Outlined.Settings)


            // creating a list of all the tabs
            val tabBarItems = listOf(homeTab, alertsTab, settingsTab)

            // creating our navController
            val navController = rememberNavController()
            MalawiLanguagesDictionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val firebaseDatabase = FirebaseDatabase.getInstance();
                    val databaseReference = firebaseDatabase.getReference("dictionary");
//                    Greeting("Android")
                    Scaffold(bottomBar = { TabView(tabBarItems, navController) }) { innerPadding ->
                        NavHost(navController = navController, startDestination = homeTab.title) {
                            composable(homeTab.title) {
                                homeTab(
                                    navController= navController,
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                            composable(alertsTab.title) {
                                Text(alertsTab.title)
                            }
                            composable(settingsTab.title) {
                                Text(settingsTab.title)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MalawiLanguagesDictionaryTheme {
        Greeting("Android")
    }
}