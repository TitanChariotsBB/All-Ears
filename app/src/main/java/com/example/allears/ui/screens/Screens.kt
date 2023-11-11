package com.example.allears.ui.screens

sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
    object About: Screens("about_screen")
    object Note: Screens("note_screen")
    object Interval: Screens("interval_screen")
    object Chord: Screens("chord_screen")
    object NoteSettings: Screens("note_settings_screen")
    object IntervalSettings: Screens("interval_settings_screen")
    object ChordSettings: Screens("chord_settings_screen")
}

fun canGoBack(route: String?): Boolean {
    if (route != null) {
        return route != "home_screen"
    }
    return true;
}

fun canShowSettings(route: String?): Boolean {
    if (route != null) {
        if (route == "note_screen" || route == "interval_screen" || route == "chord_screen") return true
    }
    return false
}

fun canShowAbout(route: String?): Boolean {
    if (route != null) {
        if (route == "home_screen") return true
    }
    return false
}

fun findSettingsRoute(route: String?): String {
    when (route) {
        "note_screen" -> return "note_settings_screen"
        "interval_screen" -> return "interval_settings_screen"
        "chord_screen" -> return "chord_settings_screen"
        else -> return "home_screen"
    }
}