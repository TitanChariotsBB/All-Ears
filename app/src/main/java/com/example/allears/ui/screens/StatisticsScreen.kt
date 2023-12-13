package com.example.allears.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.allears.R
import com.example.allears.data.MissedQ
import com.example.allears.data.Quiz
import com.example.allears.models.StatsVM

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier){
    var numWorstShown = 0
    val config = LocalConfiguration.current
    val VM : StatsVM = StatsVM.getInstance()
    val quizList by VM.quizzes.collectAsState()
    val missedQList by VM.missedQs.collectAsState()
    var requiredMode by remember{mutableStateOf("All")}
    val modeList: List<Quiz>
    val modeMissedQList: List<MissedQ>
    if(requiredMode == "All"){
        modeList = quizList
        modeMissedQList = missedQList
    }
    else {
        modeList = quizList.filter {
            it.mode == requiredMode
        }
        modeMissedQList = missedQList.filter{
            it.mode == requiredMode
        }
    }
    val steps = 10
    val points: List<Point> = VM.listQuizzesAsPoints(modeList)
    val screenWidth = (config.screenWidthDp - 64).dp.value

    Column(modifier = modifier
        .fillMaxSize()
        .padding(vertical = 64.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        Row(modifier = modifier
            .height(64.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround){
                Button(onClick = {requiredMode = "All"}){
                    Text("ALL")
                }
                Button(onClick = {requiredMode = "Solfege"}){
                    Text("SOLFEGE")
                }
        }
        Row(modifier = modifier
            .height(64.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround){
            Button(onClick = {requiredMode = "Interval"}){
                Text("INTERVAL")
            }
            Button(onClick = {requiredMode = "Chord"}) {
                Text("CHORD")
            }
        }
//        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally){
//
//            items(quizList){quiz->
//                Card(modifier = modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)) {
//
//                    Row(modifier = modifier.padding(8.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween) {
//                        Column() {
//                            Text("id: ${quiz.quiz_id} questions_right: ${quiz.questions_correct}")
//                            Text("questions_attempted: ${quiz.questions_attempted} mode : ${quiz.mode}")
//                        }
//                        Spacer(modifier = modifier.weight(1.0f))
//
//                        IconButton(onClick = { VM.deleteQuiz(quiz) }) {
//                            Icon(painter = painterResource(id = R.drawable.baseline_delete_24),
//                                contentDescription = null)
//                        }
//
//                    }
//
//                }
//            }
//
//        }
        if(points.size > 1) {
            var maxScore = 0F
            var minScore = 100F
            for(point in points){
                maxScore = maxScore.coerceAtLeast(point.y)
                minScore = minScore.coerceAtMost(point.y)
            }
            val xAxisData = AxisData.Builder()
                .axisStepSize(100.dp)
                //.backgroundColor(Color.Blue)
                .backgroundColor(MaterialTheme.colorScheme.surface)
                .axisLabelColor(MaterialTheme.colorScheme.onSurface)
                .axisLineColor(MaterialTheme.colorScheme.onSurface)
                .steps(points.size - 1)
                .labelData { i -> i.toString() }
                .labelAndAxisLinePadding(15.dp)
                .build()

            val yAxisData = AxisData.Builder()
                .steps(steps)
//                .backgroundColor(Color.Red)
                .backgroundColor(MaterialTheme.colorScheme.surface)
                .axisLabelColor(MaterialTheme.colorScheme.onSurface)
                .axisLineColor(MaterialTheme.colorScheme.onSurface)
                .labelAndAxisLinePadding(20.dp)
                .labelData { i ->
                    val yScale = (maxScore-minScore)/steps
                    (i * yScale + minScore).formatToSinglePrecision()
                }.build()

            val lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = points,
                            LineStyle(color = MaterialTheme.colorScheme.onSurfaceVariant),
                            IntersectionPoint(color = MaterialTheme.colorScheme.onSurfaceVariant),
                            SelectionHighlightPoint(),
                            ShadowUnderLine(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ), alpha = 0.5f
                            ),
                            SelectionHighlightPopUp()
                        )
                    ),
                ),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                gridLines = GridLines(),
//                backgroundColor = Color.White
                backgroundColor = MaterialTheme.colorScheme.surface
            )
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartData
            )
        }
        else if(points.size == 1){
            Text("Your Only Quiz: ${points[0].y}")
        }

        if(modeMissedQList.isNotEmpty()) {
            val correctAnswers = modeMissedQList.map{
                it.correctAnswer
            }
            val eMap:Map<String, Int> = emptyMap()
            var answerMap = correctAnswers.fold(eMap) {map:Map<String,Int>, nextEntry ->
                map.plus(Pair(nextEntry, map.getOrDefault(nextEntry, 0) + 1))
            }
            numWorstShown = answerMap.size.coerceAtMost(3)
            for(i in 0 until numWorstShown){
                val toAdd = ((answerMap.maxBy {it.value}).key)
                Row(modifier = modifier.fillMaxWidth()) {
                    Text("#${i+1} Worst Question: $toAdd")
                }
                answerMap = answerMap.minus(toAdd)
            }
        }
    }
}