package com.example.kotlinquiz.ui.main.util

import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.QuizEntity

sealed class AnswerState {
    object Loading : AnswerState()
    data class Success(val question: QuestionEntity?, val isLastQuestion: Boolean) : AnswerState()
    data class Failure(val errorMessage: String) : AnswerState()
}