package com.example.allears.models

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.allears.AllEarsApp
import com.example.allears.data.Quiz
import com.example.allears.data.QuizDao
import com.example.allears.data.QuizData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class StatsVM(
    private val quizDao: QuizDao
): ViewModel() {
    val quizzes = quizDao.getAllQuizzes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var highestIdNumber:Int = 0
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
    fun listQuizzesAsPoints(quizzes: List<Quiz>) : List<Point>{
        val points : List<Point> = quizzes.map{
            Point(it.quiz_id.toFloat(),
                (it.questions_correct/(it.questions_attempted).toFloat()) * 100)
        }
        return points
    }
    fun addQuiz(quiz: Quiz){
        highestIdNumber++
        viewModelScope.launch{
            quizDao.upsertQuiz(quiz)
        }
    }

    fun deleteQuiz(quiz: Quiz){
        viewModelScope.launch{
            quizDao.deleteQuiz(quiz)
        }
    }

    fun updateIDNumber(){
        highestIdNumber = quizzes.value.size
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