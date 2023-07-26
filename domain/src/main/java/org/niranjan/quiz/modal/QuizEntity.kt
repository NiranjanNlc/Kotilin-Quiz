package org.niranjan.quiz.modal

data class QuizEntity(
    val id: String,
    val userId : String,
    val questions: List<QuestionEntity>,
    val scores: List<ScoreEntity>,
    val startTime: Long,
    val duration: Long,
    val isFinished : Boolean = false
)