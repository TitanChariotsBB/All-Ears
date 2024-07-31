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
import com.christianabbott.allears.models.IntervalVM

@Composable
fun IntervalScreen(VM: IntervalVM, context: Context, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 64.dp, start = 32.dp, bottom = 8.dp, end = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text(text = VM.correctAnswer)
        Text(text = VM.getRoundStats())
        OutlinedButton(
            onClick = { playInterval(VM.correctAnswer, VM.number, context) },
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
            val intervalsEnabledList = VM.intervalsEnabled.toList()
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

fun playInterval(name: String, number: Int, context: Context) {
    val fileName = name.replace(" ", "") + "_" + number
    val resourceId = context.resIdByName(fileName, "raw")
    val mediaPlayer = MediaPlayer.create(context, resourceId)
    mediaPlayer.start()
}

//@Preview
//@Composable
//fun IntervalPreview() {
//    IntervalScreen()
//}
