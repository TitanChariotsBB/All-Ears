package com.example.allears.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.allears.R

@Composable
fun IntervalScreen(modifier: Modifier = Modifier) {
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

@Preview
@Composable
fun IntervalPreview() {
    IntervalScreen()
}
