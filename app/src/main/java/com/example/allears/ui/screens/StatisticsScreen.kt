package com.example.allears.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
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
import com.example.allears.models.StatsVM

@Composable
fun StatisticsScreen(modifier: Modifier = Modifier){
    val VM : StatsVM = StatsVM.getInstance()
    val quizList by VM.quizzes.collectAsState()
    val steps = 20

    val points: List<Point> = VM.listQuizzesAsPoints(quizList)

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

                    }
                }
            }
        }
        if(points.size > 0) {
            val xAxisData = AxisData.Builder()
                .axisStepSize(100.dp)
                .backgroundColor(Color.Blue)
                .steps(points.size - 1)
                .labelData { i -> i.toString() }
                .labelAndAxisLinePadding(15.dp)
                .build()

            val yAxisData = AxisData.Builder()
                .steps(steps)
                .backgroundColor(Color.Red)
                .labelAndAxisLinePadding(20.dp)
                .labelData { i ->
                    val yScale = 100 / steps
                    (i * yScale).toString()
                }.build()

            val lineChartData = LineChartData(
                linePlotData = LinePlotData(
                    lines = listOf(
                        Line(
                            dataPoints = points,
                            LineStyle(),
                            IntersectionPoint(),
                            SelectionHighlightPoint(),
                            ShadowUnderLine(),
                            SelectionHighlightPopUp()
                        )
                    ),
                ),
                xAxisData = xAxisData,
                yAxisData = yAxisData,
                gridLines = GridLines(),
                backgroundColor = Color.White
            )
            LineChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                lineChartData = lineChartData
            )
        }
    }
}