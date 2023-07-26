package org.niranjan.quiz.repo

import org.niranjan.quiz.modal.ScoreEntity

interface ScoreRepository {
    fun saveScore(score: ScoreEntity)
    fun getScoreByQuizAndUser(quizId: String, userId: String): ScoreEntity?
    fun getScoresByQuiz(quizId: String): List<ScoreEntity>
    fun getScoresByUser(userId: String): List<ScoreEntity>
    fun gethighestFivecore():List<ScoreEntity>
}