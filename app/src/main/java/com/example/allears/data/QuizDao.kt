package com.example.allears.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao{
    @Insert
    suspend fun insertQuiz(quiz:Quiz)

    @Upsert
    suspend fun upsertQuiz(quiz:Quiz)

    @Delete
    suspend fun deleteQuiz(quiz:Quiz)

    @Query("SELECT * FROM quiz")
    fun getAllQuizzes(): Flow<List<Quiz>>

    @Query("SELECT * FROM quiz WHERE mode = 'Solfege'")
    fun getSolfegeQuizzes(): Flow<List<Quiz>>

    @Query("SELECT * FROM quiz WHERE mode = 'Interval'")
    fun getIntervalQuizzes(): Flow<List<Quiz>>

    @Query("SELECT * FROM quiz WHERE mode = 'Chord'")
    fun getChordQuizzes(): Flow<List<Quiz>>

    @Query("SELECT MAX(quiz_id) FROM quiz")
    fun getMaxId(): Int
}