package com.example.allears.ui.screens

import android.content.Context
import android.graphics.Outline
import android.graphics.drawable.shapes.Shape
import android.media.MediaPlayer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.allears.R
import com.example.allears.ui.models.NoteVM
import com.example.allears.ui.theme.NoteConverter
import kotlin.random.Random

@Composable
fun NoteScreen(VM : NoteVM, modifier: Modifier = Modifier) {
    val uiState by VM.noteUIState.collectAsState()
    var currentNote by rememberSaveable{mutableStateOf('H')}
    var hasNote by rememberSaveable{mutableStateOf(false)}
    var score by rememberSaveable{mutableStateOf(0)}
    val context = LocalContext.current

    /*
    * TODO:
    * Implement a ViewModel that keeps track of:
    *   - current midi file
    *   - current correct answer
    *   - number of correct answers (optional, for calculating user's percent accuracy)
    *   - (all these things COULD be in saveable variables, but a VM makes the most sense)
    *   - (each mode will probably create a different instance of the ViewModel class)
    * Implement a button towards the top of the screen that plays audio
    * Implement a system for displaying the appropriate options as buttons
    * Implement a settings screen (different for each mode. Should probably save preferences in a file)
    *
    *
    *
    */

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Note: $currentNote Score: ${uiState.score}")
        OutlinedButton(
            onClick = {
                val enabledNotes = ((uiState.noteMap.filter { it.value }).keys).toList()
                currentNote = enabledNotes[Random.nextInt(0, enabledNotes.size)]
                if(!hasNote) hasNote = true
                playNotes(context, currentNote) },
            modifier = modifier
                .padding(32.dp)
                .size(172.dp),
            shape = RoundedCornerShape(32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_volume_up_24),
                contentDescription = null,
                modifier = modifier.size(128.dp)
            )
        }
        Column {
            uiState.notes.forEach{
                if(uiState.noteMap.get(it) == true) {
                    AnswerButton(text = it.toString(), onClick = {
                        if(it == currentNote && hasNote) {
                            VM.scoreChanged(uiState.score + 1)
                            hasNote = false
                        }
                    })
                }
            }
        }
    }
}


@Composable
fun AnswerButton(text: String, onClick: ()->Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text)
    }
}

fun playNotes(context : Context, currentNote : Char){
    val converter : NoteConverter = NoteConverter()
    val fileName = currentNote.toString().lowercase() + "5";
    val mediaPlayer = MediaPlayer.create(context, converter.converterMap.get(fileName)!!)
    mediaPlayer.start()
}

//@Preview
//@Composable
//fun NotePreview() {
//    NoteScreen()
//}