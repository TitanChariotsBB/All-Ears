package com.example.allears.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allears.AllEarsApp
import com.example.allears.data.Quiz
import com.example.allears.data.QuizDao
import com.example.allears.data.QuizData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StatsVM(
    private val quizDao: QuizDao
): ViewModel() {
    val quizzes = quizDao.getAllQuizzes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    init{
        createQuizTable()
    }

    private fun createQuizTable(){
        viewModelScope.launch{
            QuizData.listOfQuizzes.forEach{
                quizDao.upsertQuiz(it)
            }
        }
    }

    private fun addQuiz(quiz: Quiz){
        viewModelScope.launch{
            quizDao.upsertQuiz(quiz)
        }
    }

    private fun deleteQuiz(quiz: Quiz){
        viewModelScope.launch{
            quizDao.deleteQuiz(quiz)
        }
    }

    companion object{
        private var INSTANCE: StatsVM? = null

        fun getInstance(): StatsVM{
            val vm = INSTANCE ?: synchronized(this){
                StatsVM(
                    AllEarsApp.getApp().container.quizDao).also{
                    INSTANCE = it
                }
            }
            return vm
        }

    }
}