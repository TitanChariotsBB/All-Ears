package com.christianabbott.allears

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.christianabbott.allears.models.ChordVM
import com.christianabbott.allears.models.IntervalVM
import com.christianabbott.allears.models.SolfegeVM
import com.christianabbott.allears.ui.screens.AboutScreen
import com.christianabbott.allears.ui.screens.ChordScreen
import com.christianabbott.allears.ui.screens.HomeScreen
import com.christianabbott.allears.ui.screens.IntervalScreen
import com.christianabbott.allears.ui.screens.Screens
import com.christianabbott.allears.ui.screens.SettingsScreen
import com.christianabbott.allears.ui.screens.SolfegeScreen
import com.christianabbott.allears.ui.screens.canGoBack
import com.christianabbott.allears.ui.screens.canShowAbout
import com.christianabbott.allears.ui.screens.canShowSettings
import com.christianabbott.allears.ui.screens.canShowShare
import com.christianabbott.allears.ui.screens.findSettingsRoute
import com.christianabbott.allears.ui.screens.getPrettyTitle
import com.christianabbott.allears.ui.theme.AllEarsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            AllEarsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
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
    val solfegeVM: SolfegeVM = viewModel()
    val intervalVM: IntervalVM = viewModel()
    val chordVM: ChordVM = viewModel()
    Scaffold(
        topBar = {
            AllEarsTopBar(
                canNavigateBack = canGoBack(currentScreenHandler?.destination?.route),
                navigateBack = { navController.navigateUp() },
                title = getPrettyTitle(currentScreenHandler?.destination?.route),
                canShowSettings = canShowSettings(currentScreenHandler?.destination?.route),
                goToSettings = { navController.navigate(findSettingsRoute(currentScreenHandler?.destination?.route)) },
                canShowAbout = canShowAbout(currentScreenHandler?.destination?.route),
                goToAbout = { navController.navigate(Screens.About.route) },
                canShowShare = canShowShare(currentScreenHandler?.destination?.route),
                goToShare = {
                    var text = "Check out my All Ears quiz results!\n"
                    when (getPrettyTitle(currentScreenHandler?.destination?.route)) {
                        "Solfege" -> text += solfegeVM.getRoundStats()
                        "Interval" -> text += intervalVM.getRoundStats()
                        "Chord" -> text += chordVM.getRoundStats()
                        else -> "0/0 correct. (0%)"
                    }
                    launchShare(text, context)
                }
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
                    toSolfegeScreen = { navController.navigate(Screens.Solfege.route) },
                    toIntervalScreen = { navController.navigate(Screens.Interval.route) },
                    toChordScreen = { navController.navigate(Screens.Chord.route) },
                    toStatsScreen = { navController.navigate(Screens.Statistics.route) }
                )
            }

            composable(route = Screens.About.route) {
                AboutScreen()
            }

            composable(route = Screens.Solfege.route) {
                SolfegeScreen(solfegeVM, context)
            }

            composable(route = Screens.Interval.route) {
                IntervalScreen(intervalVM, context)
            }

            composable(route = Screens.Chord.route) {
                ChordScreen(chordVM, context)
            }

            composable(route = Screens.SolfegeSettings.route) {
                // SolfegeSettingsScreen(solfegeVM)
                SettingsScreen(
                    options = solfegeVM.solfegeEnabled,
                    update = { solfegeVM.updateEnabledSolfege(it) }
                )
            }

            composable(route = Screens.IntervalSettings.route) {
                // IntervalSettingsScreen(intervalVM)
                SettingsScreen(
                    options = intervalVM.intervalsEnabled,
                    update = { intervalVM.updateEnabledInterval(it) }
                )
            }

            composable(route = Screens.ChordSettings.route) {
                SettingsScreen(
                    options = chordVM.chordsEnabled,
                    update = { chordVM.updateEnabledChord(it) }
                )
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
    canShowShare: Boolean,
    goToShare: ()->Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = { Text(text = title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = { navigateBack() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "Back")
                }
            }
        },
        actions = {
            if (canShowShare) {
                IconButton(
                    onClick = { goToShare() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_share_24),
                        contentDescription = "Share")
                }
            }

            if (canShowSettings) {
                IconButton(
                    onClick = { goToSettings() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_settings_24), 
                        contentDescription = "Settings")
                }
            }

            if (canShowAbout) {
                IconButton(
                    onClick = { goToAbout() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                ) {
                    Icon(painter = painterResource(id = R.drawable.baseline_info_24),
                        contentDescription = "About")
                }
            }
        }
    )
}

fun launchShare(text: String, context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }

//    if(intent.resolveActivity(packageManager) != null) {
    startActivity(context, intent, null)
//    } else {
//        println("No app found to handle email")
//    }
}