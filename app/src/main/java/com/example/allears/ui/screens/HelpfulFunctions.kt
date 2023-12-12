package com.example.allears.ui.screens

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.allears.data.Quiz
import com.example.allears.models.StatsVM
import java.time.LocalDate

fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}

fun showFeedbackToast(correct: Boolean, context: Context): Boolean {
    val text = if (correct) "Correct!" else "Incorrect."
    val duration = Toast.LENGTH_SHORT
    val toast = Toast.makeText(context, text, duration)
    toast.show()
    return correct
}

@RequiresApi(Build.VERSION_CODES.O)
fun addDatabaseEntry(quiz_id:Int, mode:String, questions_correct:Int, questions_attempted:Int){
    val VM : StatsVM = StatsVM.getInstance()
    val today: String = LocalDate.now().toString()
    val q = Quiz(quiz_id, mode, questions_correct, questions_attempted, today)
    if(questions_attempted >= 1) {
        VM.addQuiz(q)
    }
}

