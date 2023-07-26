package com.example.kotlinquiz.ui.main.util

import org.niranjan.quiz.modal.QuizEntity

sealed class QuizState {
    object Loading : QuizState()
    data class Success(val quiz: QuizEntity) : QuizState()
    data class Failure(val error: String) : QuizState()
}
