package com.example.kotlinquiz.ui.main.util

import org.niranjan.quiz.modal.QuestionEntity

sealed class AnswerState {
    object NotAnswered : AnswerState()
    data class Success(val question: QuestionEntity?, val isLastQuestion: Boolean) : AnswerState()
    data class Failure(val errorMessage: String) : AnswerState()
}