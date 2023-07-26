package org.niranjan.quiz.modal

data class AnswerEntity(
    val id: String,
    val questionId: String,
    val text: String,
    val isCorrect: Boolean
)