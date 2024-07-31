package com.christianabbott.allears.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christianabbott.allears.ui.theme.AllEarsTheme

@Composable
fun HomeScreen(
    toNoteScreen: ()->Unit, toSolfegeScreen: ()->Unit, toIntervalScreen: ()->Unit,
    toChordScreen: ()->Unit, toStatsScreen:()->Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(top = 72.dp, bottom = 8.dp, start = 18.dp, end = 18.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        ModeCard(
//            title = "Single Note",
//            description = "Practice identifying notes or scale degrees in the context of a key",
//            onClick = { toNoteScreen() }
//        )
        ModeCard(
            title = "Solfege",
            description = "Practice identifying scale degrees (using solfege syllables)",
            onClick = { toSolfegeScreen() }
        )
        ModeCard(
            title = "Interval",
            description = "Practice identifying intervals",
            onClick = { toIntervalScreen() }
        )
        ModeCard(
            title = "Chord",
            description = "Practice identifying major, minor, augmented, diminished triads",
            onClick = { toChordScreen() }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeCard(title: String, description: String, onClick: ()->Unit, modifier: Modifier = Modifier) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().padding(bottom = 24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 8.dp)
            )
            Text(text = description, fontSize = 16.sp)
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    AllEarsTheme {
        HomeScreen({}, {}, {}, {}, {})
    }
}