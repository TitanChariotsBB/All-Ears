package com.christianabbott.allears.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.lang.Math.round

class ChordVM: ViewModel() {
    val listOfChords = listOf<String>("maj", "min", "dim", "aug")
    var numRoundsCompleted by mutableStateOf(0)
    var numAttemptsAtCurrentQuestion by mutableStateOf(0)
    var attemptedQuestions by mutableStateOf(mutableListOf<String>())
    var score by mutableStateOf(0)
    var correctAnswer by mutableStateOf("")
    // number refers to the range of the interval (1-25)
    var number by mutableStateOf(1)
    var chordsEnabled by mutableStateOf(mutableMapOf<String, Boolean>())

    init {
        // Default to major and minor triads
        for (chord in listOfChords) {
            if (chord.startsWith("m")) {
                chordsEnabled.put(chord, true)
            } else {
                chordsEnabled.put(chord, false)
            }
        }
        generateNewCorrectAnswer()
    }

    fun startNewRound() {
        if (numAttemptsAtCurrentQuestion < 1) {
            score++
        }
        numRoundsCompleted++
        numAttemptsAtCurrentQuestion = 0
        attemptedQuestions.clear()
        generateNewCorrectAnswer()
    }

    fun updateEnabledChord(name: String) {
        // flip the boolean for the requested interval name
        chordsEnabled.set(name, !chordsEnabled[name]!!)
        generateNewCorrectAnswer()
    }

    fun generateNewCorrectAnswer() {
        // get an interval from the list of enabled intervals
        correctAnswer = chordsEnabled.filter{it.value}.toList().random().first
        number = (1..13).random()
    }

    // Function returns true if attempt is correct, false if not
    fun onAnswerAttempt(chord: String): Boolean {
        if (chord == correctAnswer) {
            startNewRound()
            return true
        } else {
            numAttemptsAtCurrentQuestion++
            attemptedQuestions.add(chord)
            return false
        }
    }

    fun getRoundStats(): String {
        if (numRoundsCompleted == 0) return "0/0 correct. (0%)"
        return score.toString() + "/" + numRoundsCompleted + " correct (" + round((score.toDouble() / numRoundsCompleted.toDouble()) * 100.0) + "%)"
    }

    fun revealWrongAnswer(name: String): Boolean {
        return (attemptedQuestions.contains(name))
    }

    fun onLeaveQuiz(){
        numRoundsCompleted = 0
        numAttemptsAtCurrentQuestion = 0
        score = 0
    }
}