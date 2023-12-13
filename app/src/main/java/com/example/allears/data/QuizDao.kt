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

@Dao
interface MissedQDao{
    @Insert
    suspend fun insertMissedQ(missedQ:MissedQ)

    @Upsert
    suspend fun upsertMissedQ(missedQ:MissedQ)

    @Delete
    suspend fun deleteMissedQ(missedQ:MissedQ)

    @Query("SELECT * FROM missedQ")
    fun getAllMissedQ():Flow<List<MissedQ>>

    @Query("SELECT * FROM missedQ WHERE mode = 'Solfege'")
    fun getSolfegeMissedQ():Flow<List<MissedQ>>

    @Query("SELECT * FROM missedQ WHERE mode = 'Interval'")
    fun getIntervalMissedQ():Flow<List<MissedQ>>

    @Query("SELECT * FROM missedQ WHERE mode = 'Chord'")
    fun getChordMissedQ():Flow<List<MissedQ>>
}