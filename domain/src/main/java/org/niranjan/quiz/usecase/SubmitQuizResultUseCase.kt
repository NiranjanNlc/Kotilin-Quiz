package org.niranjan.quiz.usecase

import org.niranjan.quiz.modal.ScoreEntity
import org.niranjan.quiz.modal.UserEntity
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.repo.UserRepository

class SubmitQuizResultUseCase(
    private val quizRepository: QuizRepository,
    private val userRepository: UserRepository,
    private val scoreRepository: ScoreRepository,
) {
    fun submitQuizResult(quizId: String, user: UserEntity): List<ScoreEntity> {
        val quiz = quizRepository.getQuizById(quizId)
        val score = scoreRepository.getScoresByQuiz(quizId)
        if (quiz != null && quiz.isFinished.not()) {
            val quiz1 = quiz.copy(isFinished = true)
            quizRepository.updateQuiz(quiz1)
            userRepository.updateUser(user)
        }
        return scoreRepository.gethighestFivecore()
    }
}
