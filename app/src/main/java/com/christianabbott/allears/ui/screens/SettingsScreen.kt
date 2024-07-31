package com.christianabbott.allears.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(options: MutableMap<String, Boolean>, update: (option: String)->Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 24.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(bottom = 16.dp)
        )

        for (pair in options) {
            OptionCheckBox(
                text = pair.key.replace("_", ""),
                isChecked = pair.value,
                onChange = { update(pair.key) }
            )
        }
    }
}

@Composable
fun OptionCheckBox(text: String, isChecked: Boolean, onChange: ()->Unit, modifier: Modifier = Modifier) {
    val checkedState = rememberSaveable{
        mutableStateOf(isChecked)
    }

    Row {
        Checkbox(checked = checkedState.value, onCheckedChange = { checkedState.value = it; onChange() })
        Text(text = text, modifier = modifier.align(Alignment.CenterVertically))
    }
}