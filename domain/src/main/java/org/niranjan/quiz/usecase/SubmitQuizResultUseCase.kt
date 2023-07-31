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
    fun submitQuizResult( ): FinalResult{
        val quiz = quizRepository.getCurrentQuiz()

        if (quiz != null) {
            val quiz1 = quiz.copy(isFinished = true)
            quizRepository.updateQuiz(quiz1)
            val score = quiz.let { scoreRepository.getScoresByQuiz(it.id) }
            Log.i("result", "submitQuizResult: ${score?.sumBy { it.score }}")
        return FinalResult.Success(quiz1.userId, score?.sumBy { it.score } ?: 0)
        } else {
            return FinalResult.Failure("Quiz not found")
        }
    }
}
