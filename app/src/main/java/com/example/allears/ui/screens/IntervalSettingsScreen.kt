package com.example.allears.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allears.models.IntervalVM

@Composable
fun IntervalSettingsScreen(VM: IntervalVM, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 24.dp)
    ) {
        Text(text = "Settings", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(text = "(Note Mode)", fontSize = 18.sp)
        for (pair in VM.intervalsEnabled) {
            IntervalCheckBox(text = pair.key, isChecked = pair.value, onChange = { VM.updateEnabledInterval(pair.key) })
        }
    }
}

@Composable
fun IntervalCheckBox(text: String, isChecked: Boolean, onChange: ()->Unit, modifier: Modifier = Modifier) {
    val checkedState = rememberSaveable{
        mutableStateOf(isChecked)
    }

    Row {
        Checkbox(checked = checkedState.value, onCheckedChange = { checkedState.value = it; onChange() })
        Text(text = text, modifier = modifier.align(Alignment.CenterVertically))
    }
}