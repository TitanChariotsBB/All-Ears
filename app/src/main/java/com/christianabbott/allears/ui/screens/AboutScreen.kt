package com.christianabbott.allears.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.christianabbott.allears.R

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()
        .padding(vertical = 84.dp, horizontal = 24.dp)) {
        Text(
            text = "About the app",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(bottom = 32.dp)
        )

        Text(
            text = stringResource(id = R.string.about),
            fontSize = 18.sp,
            modifier = modifier.padding(bottom = 32.dp)
        )
    }
}

@Preview
@Composable
fun AboutPreview() {
    AboutScreen()
}