package com.example.allears.ui.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class NoteUIState(
    val notes : List<Char> = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G'),
    val noteMap : Map<Char, Boolean> = emptyMap(),
    val score : Int = 0
)
class NoteVM : ViewModel(){
    private var _noteUIState = MutableStateFlow<NoteUIState>(NoteUIState())
    val noteUIState = _noteUIState.asStateFlow()

    init {
        val noteMap : Map<Char, Boolean> = _noteUIState.value.notes.fold(emptyMap<Char, Boolean>()) {curMap, note -> curMap.plus(Pair(note, true))}
        _noteUIState.value = _noteUIState.value.copy(noteMap = noteMap, score = 0)
    }

    fun noteMapChanged(note : Char, isActive : Boolean){
        _noteUIState.value = _noteUIState.value.copy(
            noteMap = _noteUIState.value.noteMap.plus(Pair(note, !isActive))
        )
    }

    fun scoreChanged(newScore : Int){
        _noteUIState.value = _noteUIState.value.copy(
            score = newScore
        )
    }
}