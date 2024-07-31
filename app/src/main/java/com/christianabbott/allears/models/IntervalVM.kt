package com.christianabbott.allears.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.lang.Math.round

class IntervalVM: ViewModel() {
    val listOfIntervals = listOf<String>("min 2", "maj 2", "min 3", "maj 3", "perf 4", "tritone", "perf 5", "min 6", "maj 6", "min 7", "maj 7", "octave")
    var numRoundsCompleted by mutableStateOf(0)
    var numAttemptsAtCurrentQuestion by mutableStateOf(0)
    var attemptedQuestions by mutableStateOf(mutableListOf<String>())
    var score by mutableStateOf(0)
    var correctAnswer by mutableStateOf("")
    // number refers to the range of the interval (1-25)
    var number by mutableStateOf(1)
    var intervalsEnabled by mutableStateOf(mutableMapOf<String, Boolean>())

    init {
        // Default to major and perfect intervals
        for (interval in listOfIntervals) {
            if (interval.startsWith("maj") || interval.startsWith("perf")) {
                intervalsEnabled.put(interval, true)
            } else {
                intervalsEnabled.put(interval, false)
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

    fun updateEnabledInterval(name: String) {
        // flip the boolean for the requested interval name
        intervalsEnabled.set(name, !intervalsEnabled[name]!!)
        generateNewCorrectAnswer()
    }

    fun generateNewCorrectAnswer() {
        // get an interval from the list of enabled intervals
        correctAnswer = intervalsEnabled.filter{it.value}.toList().random().first
        number = (1..25).random()
    }

    // Function returns true if attempt is correct, false if not
    fun onAnswerAttempt(interval: String): Boolean {
        if (interval == correctAnswer) {
            startNewRound()
            return true
        } else {
            numAttemptsAtCurrentQuestion++
            attemptedQuestions.add(interval)
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