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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.christianabbott.allears.R
import com.christianabbott.allears.models.ChordVM

@Composable
fun ChordScreen(VM: ChordVM, context: Context, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 32.dp, bottom = 8.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(text = VM.correctAnswer)
        Text(text = VM.getRoundStats())
        OutlinedButton(
            onClick = { playChord(VM.correctAnswer, VM.number, context) },
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

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            val intervalsEnabledList = VM.chordsEnabled.toList()
            items(intervalsEnabledList) {
                AnswerButton(
                    text = it.first,
                    onClick = { VM.onAnswerAttempt(it.first) },
                    isRevealed = { VM.revealWrongAnswer(it.first) },
                    context = context,
                    enabled = it.second
                )
            }
        }
    }
}

//fun showFeedbackToast(correct: Boolean, context: Context) {
//    val text = if (correct) "Correct!" else "Incorrect."
//    val duration = Toast.LENGTH_SHORT
//    val toast = Toast.makeText(context, text, duration)
//    toast.show()
//}

//fun Context.resIdByName(resIdName: String?, resType: String): Int {
//    resIdName?.let {
//        return resources.getIdentifier(it, resType, packageName)
//    }
//    throw Resources.NotFoundException()
//}

fun playChord(name: String, number: Int, context: Context) {
    val fileName = name + "_" + number
    val resourceId = context.resIdByName(fileName, "raw")
    val mediaPlayer = MediaPlayer.create(context, resourceId)
    mediaPlayer.start()
}
