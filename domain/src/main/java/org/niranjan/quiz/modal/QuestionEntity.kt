package org.niranjan.quiz.modal

data class QuestionEntity(
    val questionId: String,
    val text: String=" ",
    val answers: List<AnswerEntity>,
    val difficultyLevel: Int,
    val category: String,
    val correctAnswer: AnswerEntity? = null,
    var isAnswered: Boolean = false,
)