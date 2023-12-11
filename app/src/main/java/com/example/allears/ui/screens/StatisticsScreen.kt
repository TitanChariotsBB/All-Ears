package com.example.allears.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.allears.R
import com.example.allears.models.StatsVM

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier){
    val VM : StatsVM = StatsVM.getInstance()
    val quizList by VM.quizzes.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(vertical = 64.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally){
            items(quizList){quiz ->
                Card(modifier = modifier.fillMaxWidth().padding(8.dp)){
                    Row(modifier = modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Column() {
                            Text("id: ${quiz.quiz_id} mode: ${quiz.mode}")
                            Text("percentage: ${quiz.questions_correct/(quiz.questions_attempted).toDouble()} date : ${quiz.date}")
                        }
                        Spacer(modifier = modifier.weight(1.0f))

                        IconButton(onClick = { VM.deleteQuiz(quiz) }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_delete_24),
                                contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}