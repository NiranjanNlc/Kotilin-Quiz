package org.niranjan.quiz.modal

data class HighScoreEntity(
    val id: String,
    val user : UserEntity,
    val quiz: QuizEntity,
    val score: ScoreEntity,
    val dateAchieved: Long
)
