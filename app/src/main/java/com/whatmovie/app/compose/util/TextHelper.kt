package com.whatmovie.app.compose.util

object TextHelper {
    fun convertRuntimeToText(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60

        return if (hours > 0) {
            String.format("%dh %02dm", hours, remainingMinutes)
        } else {
            String.format("%dm", remainingMinutes)
        }
    }
}