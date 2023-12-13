package com.example.allears.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =[Quiz::class, MissedQ::class],
    version=3
)
abstract class QuizDatabase: RoomDatabase(){
    abstract val quizDao:QuizDao
    abstract val missedQDao:MissedQDao
}