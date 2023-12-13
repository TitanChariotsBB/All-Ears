package com.example.allears

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.allears.models.ChordVM
import com.example.allears.models.IntervalVM
import com.example.allears.models.SolfegeVM
import com.example.allears.models.StatsVM
import com.example.allears.ui.screens.AboutScreen
import com.example.allears.ui.screens.ChordScreen
import com.example.allears.ui.screens.HomeScreen
import com.example.allears.ui.screens.IntervalScreen
import com.example.allears.ui.screens.Screens
import com.example.allears.ui.screens.SettingsScreen
import com.example.allears.ui.screens.SolfegeScreen
import com.example.allears.ui.screens.StatisticsScreen
import com.example.allears.ui.screens.addDatabaseEntry
import com.example.allears.ui.screens.canGoBack
import com.example.allears.ui.screens.canShowAbout
import com.example.allears.ui.screens.canShowSettings
import com.example.allears.ui.screens.findSettingsRoute
import com.example.allears.ui.screens.getPrettyTitle
import com.example.allears.ui.theme.AllEarsTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AllEarsApp(context: Context, modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentScreenHandler by navController.currentBackStackEntryAsState()
    val solfegeVM: SolfegeVM = viewModel()
    val intervalVM: IntervalVM = viewModel()
    val chordVM: ChordVM = viewModel()
    val VM : StatsVM = StatsVM.getInstance()
    VM.updateIDNumber()
    VM.updateMissedQIDNumber()
    Scaffold(
        topBar = {
            AllEarsTopBar(
                canNavigateBack = canGoBack(currentScreenHandler?.destination?.route),
                navigateBack = {
                    VM.updateIDNumber()
                    if(currentScreenHandler?.destination?.route == "solfege_screen"){
                        addDatabaseEntry(VM.highestIdNumber + 1, "Solfege", solfegeVM.score,
                            solfegeVM.numRoundsCompleted)
                        solfegeVM.onLeaveQuiz()
                    }
                    else if(currentScreenHandler?.destination?.route == "interval_screen"){
                        addDatabaseEntry(VM.highestIdNumber + 1, "Interval", intervalVM.score,
                            intervalVM.numRoundsCompleted)
                        intervalVM.onLeaveQuiz()
                    }
                    else if(currentScreenHandler?.destination?.route == "chord_screen"){
                        addDatabaseEntry(VM.highestIdNumber + 1, "Chord", chordVM.score,
                            chordVM.numRoundsCompleted)
                        chordVM.onLeaveQuiz()
                    }
                    navController.navigateUp()
                               },
                title = getPrettyTitle(currentScreenHandler?.destination?.route),
                canShowSettings = canShowSettings(currentScreenHandler?.destination?.route),
                goToSettings = { navController.navigate(findSettingsRoute(currentScreenHandler?.destination?.route)) },
                canShowAbout = canShowAbout(currentScreenHandler?.destination?.route),
                goToAbout = { navController.navigate(Screens.About.route) }
            )
        },
        bottomBar = {
            AllEarsBottomBar(
                goToHome = {navController.navigate(Screens.Home.route)},
                goToSolfege = {
                    navController.navigate(Screens.Solfege.route) },
                goToInterval = {navController.navigate(Screens.Interval.route)},
                goToChord = {navController.navigate(Screens.Chord.route)},
                goToStats = {navController.navigate(Screens.Statistics.route)},
                doExityStuff = {
                    if(currentScreenHandler?.destination?.route == "solfege_screen"){
                        addDatabaseEntry(VM.highestIdNumber + 1, "Solfege", solfegeVM.score,
                            solfegeVM.numRoundsCompleted)
                        solfegeVM.onLeaveQuiz()
                    }
                    else if(currentScreenHandler?.destination?.route == "interval_screen"){
                        addDatabaseEntry(VM.highestIdNumber + 1, "Interval", intervalVM.score,
                            intervalVM.numRoundsCompleted)
                        intervalVM.onLeaveQuiz()
                    }
                    else if(currentScreenHandler?.destination?.route == "chord_screen"){
                        addDatabaseEntry(VM.highestIdNumber + 1, "Chord", chordVM.score,
                            chordVM.numRoundsCompleted)
                        chordVM.onLeaveQuiz()
                    }
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
                    toStatsScreen = {navController.navigate(Screens.Statistics.route)}
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

            composable(route = Screens.Statistics.route) {
                StatisticsScreen()
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
    goToAbout: ()->Unit
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

@Composable
fun AllEarsBottomBar(
    goToHome: ()->Unit,
    goToSolfege: ()->Unit,
    goToInterval: ()->Unit,
    goToChord: ()->Unit,
    goToStats: ()->Unit,
    doExityStuff: ()->Unit
){
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ){
        TextButton(onClick = {
            goToSolfege()
            doExityStuff()
        }){
            Text("Solfege")
        }
        TextButton(onClick = {
            goToInterval()
            doExityStuff()
        }){
            Text("Interval")
        }
        TextButton(onClick = {
            goToChord()
            doExityStuff()
        }){
            Text("Chord")
        }
        TextButton(onClick = {
            goToStats()
            doExityStuff()
        }){
            Text("Stats")
        }
        TextButton(onClick = {
            goToHome()
            doExityStuff()
        }){
            Text("Home")
        }
    }
}