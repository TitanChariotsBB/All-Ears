package com.example.allears.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Quiz")
data class Quiz(
    @PrimaryKey
    var quiz_id:Int = 1,
    var mode:String = "",
    var questions_correct:Int = 0,
    var questions_attempted:Int = 0,
    var date: String = ""

)

@Entity(tableName = "MissedQ")
data class MissedQ(
    @PrimaryKey
    var missed_q_id:Int = 1,
    var mode:String = "",
    var correctAnswer:String = ""
)