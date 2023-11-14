package com.example.allears.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.allears.ui.models.NoteVM

@Composable
fun NoteSettingsScreen(modifier: Modifier = Modifier) {
    val VM : NoteVM = viewModel()
    val uiState by VM.noteUIState.collectAsState()
    val notes = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G')
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 24.dp)
    ) {
        Text(text = "Settings", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(text = "(Note Mode)", fontSize = 18.sp)
        for(note in notes){
            NoteButton(VM, note)
        }
    }
}

@Composable
fun NoteButton(VM : NoteVM, note : Char, modifier: Modifier = Modifier) {
    val uiState by VM.noteUIState.collectAsState()
    Button(
        onClick = { VM.noteMapChanged(note, uiState.noteMap.get(note)!!)},
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "$note: ${uiState.noteMap.get(note)}")
    }
}

@Preview
@Composable
fun NoteSettingsPreview() {
    NoteSettingsScreen()
}