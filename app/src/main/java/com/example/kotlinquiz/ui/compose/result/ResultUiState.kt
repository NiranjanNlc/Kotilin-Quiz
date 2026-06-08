package com.example.kotlinquiz.ui.compose.result

data class LeaderboardEntryUi(
    val rank: Int,
    val userName: String,
    val score: Int,
    val isCurrentUser: Boolean,
)

data class ResultUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val xpEarned: Int = 0,
    val displayedScore: Int = 0,
    val level: Int = 1,
    val didLevelUp: Boolean = false,
    val showLevelBadge: Boolean = false,
    val leaderboard: List<LeaderboardEntryUi> = emptyList(),
    val isOnLeaderboard: Boolean = false,
    val userRank: Int? = null,
    val headlineMessage: String = "",
    val subMessage: String = "",
    val errorMessage: String? = null,
)
