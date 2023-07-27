package com.example.kotlinquiz.ui.main.util

import org.niranjan.quiz.modal.QuestionEntity

sealed class ResultState {
    object Loading : ResultState()
    data class Success(val user: String, val score: Int) : ResultState()
    data class Failure(val errorMessage: String) : ResultState()

}
