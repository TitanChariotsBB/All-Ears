package com.example.allears.ui.screens

import android.graphics.Outline
import android.graphics.drawable.shapes.Shape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.allears.R

@Composable
fun NoteScreen(onBack: ()->Unit, modifier: Modifier = Modifier) {
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
        OutlinedButton(
            onClick = { /*TODO*/ },
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
            AnswerButton(text = "C", onClick = { /*TODO*/ })
            AnswerButton(text = "D", onClick = { /*TODO*/ })
            AnswerButton(text = "E", onClick = { /*TODO*/ })
            AnswerButton(text = "F", onClick = { /*TODO*/ })
            AnswerButton(text = "G", onClick = { /*TODO*/ })
            AnswerButton(text = "A", onClick = { /*TODO*/ })
            AnswerButton(text = "B", onClick = { /*TODO*/ })
        }
    }
}

@Composable
fun AnswerButton(text: String, onClick: ()->Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = { onClick },
        modifier = modifier
            .padding(8.dp)
            .size(64.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun NotePreview() {
    NoteScreen({})
}