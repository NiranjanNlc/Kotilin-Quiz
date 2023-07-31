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
            val score = scoreRepository.getScoresByQuiz(quiz.id)
                .sumOf { it.score } * 10
            Log.i("result", "submitQuizResult: $score")
        return FinalResult.Success(quiz1.userId, score ?: 0)
        } else {
            return FinalResult.Failure("Quiz not found")
        }
    }
}
