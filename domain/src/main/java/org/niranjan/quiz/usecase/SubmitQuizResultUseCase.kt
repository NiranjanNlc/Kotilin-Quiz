package org.niranjan.quiz.usecase

import android.util.Log
import org.niranjan.quiz.modal.ScoreEntity
import org.niranjan.quiz.modal.UserEntity
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.repo.UserRepository
import org.niranjan.quiz.result.FinalResult

class SubmitQuizResultUseCase(
    private val quizRepository: QuizRepository,
    private val scoreRepository: ScoreRepository,
) {
    fun submitQuizResult(quizId: String): FinalResult {
        val quiz = quizRepository.getQuizById(quizId)
        if (quiz != null) {
            val correctCount = scoreRepository.getScoresByQuiz(quiz.id).sumOf { it.score }
            val totalQuestions = quiz.questions.size
            Log.i("result", "submitQuizResult: $correctCount / $totalQuestions for ${quiz.userId}")
            return FinalResult.Success(quiz.userId, correctCount, totalQuestions)
        }
        return FinalResult.Failure("Quiz not found")
    }
}
