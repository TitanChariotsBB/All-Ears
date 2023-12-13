package com.example.allears.models


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.allears.AllEarsApp
import com.example.allears.data.MissedQ
import com.example.allears.data.MissedQDao
import com.example.allears.data.Quiz
import com.example.allears.data.QuizDao
import com.example.allears.data.QuizData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StatsVM(
    private val quizDao: QuizDao,
    private val missedQDao: MissedQDao
): ViewModel() {
    val quizzes = quizDao.getAllQuizzes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val missedQs = missedQDao.getAllMissedQ()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var highestIdNumber:Int = 0
    var highestMissedQIdNumber:Int = 0
    init{
        createQuizTable()
        createMissedQTable()
    }

    private fun createQuizTable(){
        viewModelScope.launch{
            QuizData.listOfQuizzes.forEach{
                quizDao.upsertQuiz(it)
            }
        }
    }
    private fun createMissedQTable(){
        viewModelScope.launch{
            QuizData.listOfMissedQ.forEach{
                missedQDao.upsertMissedQ(it)
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

    fun addMissedQ(missedQ: MissedQ){
        highestMissedQIdNumber++
        viewModelScope.launch{
            missedQDao.upsertMissedQ(missedQ)
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

    fun updateMissedQIDNumber(){
        highestMissedQIdNumber = missedQs.value.size
    }


    companion object{
        private var INSTANCE: StatsVM? = null

        fun getInstance(): StatsVM{
            val vm = INSTANCE ?: synchronized(this){
                StatsVM(
                    AllEarsApp.getApp().container.quizDao,
                    AllEarsApp.getApp().container.missedQDao).also{
                    INSTANCE = it
                }
            }
            return vm
        }

    }
}