package com.christianabbott.allears.ui.screens

import android.content.Context
import android.content.res.Resources
import android.widget.Toast

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

