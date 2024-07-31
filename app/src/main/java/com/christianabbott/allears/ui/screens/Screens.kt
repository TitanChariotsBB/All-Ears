package com.christianabbott.allears.ui.screens

sealed class Screens(val route: String) {
    object Home: Screens("home_screen")
    object About: Screens("about_screen")
    object Note: Screens("note_screen")
    object Solfege: Screens("solfege_screen")
    object Interval: Screens("interval_screen")
    object Chord: Screens("chord_screen")
    object NoteSettings: Screens("note_settings_screen")
    object SolfegeSettings: Screens("solfege_settings_screen")
    object IntervalSettings: Screens("interval_settings_screen")
    object ChordSettings: Screens("chord_settings_screen")
    object Statistics: Screens("statistics_screen")
}

fun canGoBack(route: String?): Boolean {
    if (route != null) {
        return route != "home_screen"
    }
    return true;
}

fun canShowSettings(route: String?): Boolean {
    if (route != null) {
        if (route != "home_screen" && route != "about_screen" && route != "statistics_screen" && !route.contains("settings")) return true
    }
    return false
}

fun canShowAbout(route: String?): Boolean {
    if (route != null) {
        if (route == "home_screen") return true
    }
    return false
}

fun canShowShare(route: String?): Boolean {
    if (route != null) {
        if (route.contains("solfege") || route.contains("interval") || route.contains("chord")) return true
    }
    return false
}

fun findSettingsRoute(route: String?): String {
    when (route) {
        "note_screen" -> return "note_settings_screen"
        "solfege_screen" -> return "solfege_settings_screen"
        "interval_screen" -> return "interval_settings_screen"
        "chord_screen" -> return "chord_settings_screen"
        else -> return "home_screen"
    }
}

fun getPrettyTitle(route: String?): String {
    if (route != null) {
        var str = route.replace("_", "")
            .replace("screen", "").replace("settings", "")
            .replaceFirstChar{it.uppercase()}.trim()
        return if (str == "Home" || str == "About") "All Ears" else str
    } else return "All Ears"
}