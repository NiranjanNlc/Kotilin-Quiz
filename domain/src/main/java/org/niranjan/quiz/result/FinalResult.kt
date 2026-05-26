package org.niranjan.quiz.result

sealed class FinalResult {
    data class LeaderboardEntry(
        val rank: Int,
        val userName: String,
        val score: Int,
        val isCurrentUser: Boolean,
    )

    data class Success(
        val user: String,
        val score: Int,
        val totalQuestions: Int,
        val leaderboard: List<LeaderboardEntry>,
        val isOnLeaderboard: Boolean,
        val userRank: Int?,
    ) : FinalResult()

    data class Failure(val errorMessage: String) : FinalResult()
}