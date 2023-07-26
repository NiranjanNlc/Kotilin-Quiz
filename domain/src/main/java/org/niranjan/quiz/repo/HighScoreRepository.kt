package org.niranjan.quiz.repo

import org.niranjan.quiz.modal.HighScoreEntity


interface HighScoreRepository {
    fun saveHighScore(highScore: HighScoreEntity)
    fun getTopHighScores(limit: Int): List<HighScoreEntity>
    fun getHighScoresByUser(userId: String): List<HighScoreEntity>
}