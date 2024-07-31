package com.christianabbott.allears.ui.screens

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christianabbott.allears.R
import com.christianabbott.allears.models.SolfegeVM

@Composable
fun SolfegeScreen(VM: SolfegeVM, context: Context, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 32.dp, bottom = 8.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = VM.getRoundStats())
        OutlinedButton(
            onClick = { playSolfege(VM.correctAnswer, context) },
            modifier = modifier
                .padding(32.dp)
                .size(172.dp),
            shape = RoundedCornerShape(32.dp),
            //colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_volume_up_24),
                contentDescription = null,
                modifier = modifier.size(128.dp)
            )
        }

        OutlinedButton(
            onClick = { playSolfege("do_", context) },
            modifier = modifier.padding(bottom = 32.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(text = "Play reference pitch", fontSize = 14.sp)
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
    val defaultColor = MaterialTheme.colorScheme.onSurfaceVariant
    val wrongAnsColor = MaterialTheme.colorScheme.error
    var isCorrectAnswer: Boolean
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var color: Color by remember {
        mutableStateOf(defaultColor)
    }

    color = if (isRevealed()) wrongAnsColor else defaultColor

    Button(
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
        Text(text = text, fontSize = 14.sp)
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