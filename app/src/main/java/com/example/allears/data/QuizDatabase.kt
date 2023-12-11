package com.example.allears.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =[Quiz::class],
    version=1
)
abstract class QuizDatabase: RoomDatabase(){
    abstract val quizDao:QuizDao
}