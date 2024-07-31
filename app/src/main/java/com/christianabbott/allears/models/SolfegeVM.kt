package com.christianabbott.allears.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.lang.Math.round

class SolfegeVM: ViewModel() {
    val listOfSolfege = listOf<String>("do_", "re", "mi", "fa", "sol", "la", "ti")

    var numRoundsCompleted by mutableStateOf(0)
    var numAttemptsAtCurrentQuestion by mutableStateOf(0)
    var attemptedQuestions by mutableStateOf(mutableListOf<String>())
    var score by mutableStateOf(0)
    var correctAnswer by mutableStateOf("")
    var solfegeEnabled by mutableStateOf(mutableMapOf<String, Boolean>())

    init {
        // Default to major and perfect intervals
        for (solfege in listOfSolfege) {
            solfegeEnabled.put(solfege, true)
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

    fun updateEnabledSolfege(name: String) {
        // flip the boolean for the requested interval name
        solfegeEnabled.set(name, !solfegeEnabled[name]!!)
        generateNewCorrectAnswer()
    }

    fun generateNewCorrectAnswer() {
        // get an interval from the list of enabled intervals
        correctAnswer = solfegeEnabled.filter{it.value}.toList().random().first
    }

    // Function returns true if attempt is correct, false if not
    fun onAnswerAttempt(solfege: String): Boolean {
        if (solfege == correctAnswer) {
            startNewRound()
            return true
        } else {
            numAttemptsAtCurrentQuestion++
            attemptedQuestions.add(solfege)
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