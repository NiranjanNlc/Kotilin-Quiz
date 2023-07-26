package org.niranjan.quiz.modal

data class ScoreEntity(
    val scorId: String = "",
    val userId: String,
    val quizId: String,
    val score: Int,
    val dateAchieved: Long
)