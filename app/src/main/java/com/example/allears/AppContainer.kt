package com.example.allears

import android.content.Context
import androidx.room.Room
import com.example.allears.data.MissedQDao
import com.example.allears.data.QuizDao
import com.example.allears.data.QuizDatabase

interface AppContainer {
    val quizDao: QuizDao
    val missedQDao: MissedQDao
}

class DefaultContainer(val context: Context): AppContainer{
    private val db by lazy{
        Room.databaseBuilder(
            context,
            QuizDatabase::class.java,
            "quizdatabase.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    override val quizDao:QuizDao by lazy{
        db.quizDao
    }
    override val missedQDao: MissedQDao by lazy{
        db.missedQDao
    }
}
