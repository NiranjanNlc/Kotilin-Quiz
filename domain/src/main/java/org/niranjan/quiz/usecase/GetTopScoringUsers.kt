package org.niranjan.quiz.usecase

import org.niranjan.quiz.modal.ScoreEntity
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.repo.UserRepository

class GetTopScoringUsers  (
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository,
    private val scoreRepository: ScoreRepository,
) {
    fun  getTopScoringUsers(): List<ScoreEntity> {
        return scoreRepository.gethighestFivecore()
    }
}