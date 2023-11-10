package com.example.allears

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.allears.ui.screens.HomeScreen
import com.example.allears.ui.theme.AllEarsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllEarsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AllEarsApp()
                }
            }
        }
    }
}

@Composable
fun AllEarsApp() {
    // TODO: all the navigation stuff

    HomeScreen(
        toNoteScreen = { /*TODO*/ },
        toIntervalScreen = { /*TODO*/ },
        toChordScreen = { /*TODO*/ }
    )
}