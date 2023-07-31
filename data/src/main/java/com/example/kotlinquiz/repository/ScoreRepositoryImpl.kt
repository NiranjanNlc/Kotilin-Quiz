package com.example.kotlinquiz.repository

import com.example.kotlinquiz.local.Score
import com.example.kotlinquiz.local.dao.ScoreDao
import org.niranjan.quiz.modal.ScoreEntity
import org.niranjan.quiz.repo.ScoreRepository

class ScoreRepositoryImpl(private val scoreDao: ScoreDao) : ScoreRepository {
    override fun saveScore(score: ScoreEntity) {
        scoreDao.saveScore(Score(score.scorId,score.userId,score.quizId,score.score)
        )
    }

    override fun updateScore(score: ScoreEntity) {
         scoreDao.updateScore(Score(score.scorId,score.userId,score.quizId,score.score)
        )
    }

    override fun getScoreByQuizAndUser(
        quizId: String,
        userId: String
    ): ScoreEntity? {
        TODO("Not yet implemented")
    }

    override fun getScoresByQuiz(quizId: String): List<ScoreEntity> {
          return scoreDao.getScoresByQuiz(quizId).map {
               ScoreEntity(it.scoreId,it.userId,it.quizId,it.score,0L)
           }
    }

    override fun getScoresByUser(userId: String): List<ScoreEntity> {
        TODO("Not yet implemented")
    }

    override fun gethighestFivecore(): List<ScoreEntity> {
        TODO("Not yet implemented")
    }


}
