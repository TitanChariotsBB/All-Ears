package com.example.allears

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allears.models.IntervalVM
import com.example.allears.models.NoteVM
import com.example.allears.ui.screens.AboutScreen
import com.example.allears.ui.screens.HomeScreen
import com.example.allears.ui.screens.IntervalScreen
import com.example.allears.ui.screens.IntervalSettingsScreen
import com.example.allears.ui.screens.NoteScreen
import com.example.allears.ui.screens.NoteSettingsScreen
import com.example.allears.ui.screens.Screens
import com.example.allears.ui.screens.canGoBack
import com.example.allears.ui.screens.canShowAbout
import com.example.allears.ui.screens.canShowSettings
import com.example.allears.ui.screens.findSettingsRoute
import com.example.allears.ui.theme.AllEarsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            AllEarsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AllEarsApp(LocalContext.current)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllEarsApp(context: Context, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentScreenHandler by navController.currentBackStackEntryAsState()
    val noteVM: NoteVM = viewModel()
    val intervalVM: IntervalVM = viewModel()

    Scaffold(
        topBar = {
            AllEarsTopBar(
                canNavigateBack = canGoBack(currentScreenHandler?.destination?.route),
                navigateBack = { navController.navigateUp() },
                title = currentScreenHandler?.destination?.route ?: "All Ears",
                canShowSettings = canShowSettings(currentScreenHandler?.destination?.route),
                goToSettings = { navController.navigate(findSettingsRoute(currentScreenHandler?.destination?.route)) },
                canShowAbout = canShowAbout(currentScreenHandler?.destination?.route),
                goToAbout = { navController.navigate(Screens.About.route) }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home_screen",
            modifier = modifier.padding(8.dp)
        ) {
            composable(route = Screens.Home.route) {
                HomeScreen(
                    toNoteScreen = { navController.navigate(Screens.Note.route) },
                    toIntervalScreen = { navController.navigate(Screens.Interval.route) },
                    toChordScreen = { navController.navigate(Screens.Chord.route) }
                )
            }

            composable(route = Screens.About.route) {
                AboutScreen()
            }

            composable(route = Screens.Note.route) {
                NoteScreen(noteVM)
            }

            composable(route = Screens.Interval.route) {
                IntervalScreen(intervalVM, context)
            }

            composable(route = Screens.NoteSettings.route) {
                NoteSettingsScreen(noteVM)
            }

            composable(route = Screens.IntervalSettings.route) {
                IntervalSettingsScreen(intervalVM)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllEarsTopBar(
    canNavigateBack: Boolean,
    navigateBack: ()->Unit,
    title: String,
    canShowSettings: Boolean,
    goToSettings: ()->Unit,
    canShowAbout: Boolean,
    goToAbout: ()->Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = { Text(text = title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navigateBack() }) {
                 Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24), 
                     contentDescription = "Back") 
                }
            }
        },
        actions = {
            if (canShowSettings) {
                IconButton(onClick = { goToSettings() }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_settings_24), 
                        contentDescription = "Settings")
                }
            }

            if (canShowAbout) {
                IconButton(onClick = { goToAbout() }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "About")
                }
            }
        }
    )
}