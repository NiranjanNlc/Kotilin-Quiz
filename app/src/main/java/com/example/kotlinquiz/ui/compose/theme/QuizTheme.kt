package com.example.kotlinquiz.ui.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val QuizGreen = Color(0xFF388011)
private val QuizGreenDark = Color(0xFF2A6009)
private val CorrectGreen = Color(0xFF4CAF50)
private val IncorrectRed = Color(0xFFE53935)

private val LightColorScheme = lightColorScheme(
    primary = QuizGreen,
    onPrimary = Color.White,
    primaryContainer = QuizGreenDark,
    secondary = QuizGreen,
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onSurface = Color(0xFF363A43),
    error = IncorrectRed,
)

object QuizColors {
    val correct = CorrectGreen
    val incorrect = IncorrectRed
    val streakFlame = Color(0xFFFF9800)
    val heartFilled = Color(0xFFE53935)
    val heartEmpty = Color(0xFFBDBDBD)
    val progressFilled = QuizGreen
    val progressEmpty = Color(0xFFE0E0E0)
}

@Composable
fun QuizTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content,
    )
}
