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
            val topScores = scoreRepository.getTopScores(LEADERBOARD_SIZE)
            val leaderboard = topScores.mapIndexed { index, entry ->
                FinalResult.LeaderboardEntry(
                    rank = index + 1,
                    userName = entry.userId,
                    score = entry.score,
                    isCurrentUser = entry.userId == quiz.userId && entry.quizId == quiz.id,
                )
            }
            val userRank = leaderboard
                .firstOrNull { it.isCurrentUser }
                ?.rank
            val isOnLeaderboard = userRank != null

            Log.i("result", "submitQuizResult: $correctCount / $totalQuestions for ${quiz.userId}")
            return FinalResult.Success(
                user = quiz.userId,
                score = correctCount,
                totalQuestions = totalQuestions,
                leaderboard = leaderboard,
                isOnLeaderboard = isOnLeaderboard,
                userRank = userRank,
            )
        }
        return FinalResult.Failure("Quiz not found")
    }

    companion object {
        private const val LEADERBOARD_SIZE = 10
    }
}
