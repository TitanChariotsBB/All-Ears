package com.example.allears.ui.screens

import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.allears.R
import com.example.allears.models.SolfegeVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@Composable
fun SolfegeScreen(VM: SolfegeVM, context: Context, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = VM.getRoundStats())
        OutlinedButton(
            onClick = { playSolfege(VM.correctAnswer, context) },
            modifier = modifier
                .padding(32.dp)
                .size(172.dp),
            shape = RoundedCornerShape(32.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_volume_up_24),
                contentDescription = null,
                modifier = modifier.size(128.dp)
            )
        }

        OutlinedButton(
            onClick = { playSolfege("do_", context) },
            modifier = modifier.padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Play referrence pitch")
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            val intervalsEnabledList = VM.solfegeEnabled.toList()
            items(intervalsEnabledList) {
                AnswerButton(
                    text = it.first.replace("_", ""),
                    onClick = { VM.onAnswerAttempt(it.first) },
                    isRevealed = { VM.revealWrongAnswer(it.first) },
                    context = context,
                    enabled = it.second
                )
            }
        }
    }
}

@Composable
fun AnswerButton(text: String, onClick: ()->Boolean, isRevealed: ()->Boolean, context: Context, enabled: Boolean = true, modifier: Modifier = Modifier) {
    val defaultColor = MaterialTheme.colorScheme.primaryContainer
    val wrongAnsColor = MaterialTheme.colorScheme.errorContainer
    var isCorrectAnswer: Boolean
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var color: Color by remember {
        mutableStateOf(defaultColor)
    }

    color = if (isRevealed()) wrongAnsColor else defaultColor

    FilledTonalButton(
        onClick = {
            isCorrectAnswer = onClick()
            if (isCorrectAnswer) showFeedbackToast(true, context)
            color = if (isRevealed()) wrongAnsColor else defaultColor
        },
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(text = text)
    }

}

//@Composable
//fun correctAnswerDialog(onDismissRequest: ()->Unit) {
//    Dialog(onDismissRequest = { onDismissRequest() }) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Text(
//                text = "Correct",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.Center),
//                textAlign = TextAlign.Center,
//            )
//        }
//    }
//}



//fun showFeedbackToast(correct: Boolean, context: Context) {
//    val text = if (correct) "Correct!" else "Incorrect."
//    val duration = Toast.LENGTH_SHORT
//    val toast = Toast.makeText(context, text, duration)
//    toast.show()
//}
//
//fun Context.resIdByName(resIdName: String?, resType: String): Int {
//    resIdName?.let {
//        return resources.getIdentifier(it, resType, packageName)
//    }
//    throw Resources.NotFoundException()
//}

fun playSolfege(fileName: String, context: Context) {
    val resourceId = context.resIdByName(fileName, "raw")
    val mediaPlayer = MediaPlayer.create(context, resourceId)
    mediaPlayer.start()
}