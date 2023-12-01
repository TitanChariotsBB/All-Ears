package com.example.allears.ui.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IntervalVM: ViewModel() {
    val listOfIntervals = listOf<String>("min 2", "maj 2", "min 3", "maj 3", "perf 4", "aug 4", "dim 5", "perf 5", "min 6", "maj 6", "min 7", "maj 7")

    var numRounds by mutableStateOf(0)
    var intervalsEnabled by mutableStateOf(mutableMapOf<String, Boolean>())

    init {
        // Default to major and perfect intervals
        for (interval in listOfIntervals) {
            if (interval.startsWith("maj") || interval.startsWith("perf")) {
                intervalsEnabled.put(interval, true)
            } else {
                intervalsEnabled.put(interval, false)
            }
        }
    }

    fun updateEnabledInterval(name: String) {
        intervalsEnabled.set(name, !intervalsEnabled[name]!!)
    }


}