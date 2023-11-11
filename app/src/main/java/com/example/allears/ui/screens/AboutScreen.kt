package com.example.allears.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(vertical = 64.dp, horizontal = 24.dp)) {
        Text(text = "About the app", fontSize = 32.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun AboutPreview() {
    AboutScreen()
}