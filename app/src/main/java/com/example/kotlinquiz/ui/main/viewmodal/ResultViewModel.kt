package com.example.kotlinquiz.ui.main.viewmodal

import androidx.lifecycle.ViewModel
import com.example.kotlinquiz.ui.compose.result.LeaderboardEntryUi
import com.example.kotlinquiz.ui.compose.result.ResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.niranjan.quiz.result.FinalResult
import org.niranjan.quiz.usecase.SubmitQuizResultUseCase
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val useCase: SubmitQuizResultUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    fun loadResult(quizId: String?) {
        if (quizId.isNullOrBlank()) {
            _uiState.value = ResultUiState(
                isLoading = false,
                errorMessage = "Quiz not found",
            )
            return
        }

        _uiState.value = ResultUiState(isLoading = true)

        when (val result = useCase.submitQuizResult(quizId)) {
            is FinalResult.Success -> {
                val xpEarned = result.score * XP_PER_CORRECT
                val level = xpEarned / XP_PER_LEVEL + 1
                val didLevelUp = xpEarned >= XP_PER_LEVEL && result.score > 0
                val (headline, sub) = buildMessages(result)

                _uiState.value = ResultUiState(
                    isLoading = false,
                    userName = result.user,
                    score = result.score,
                    totalQuestions = result.totalQuestions,
                    xpEarned = xpEarned,
                    xpProgress = 0f,
                    displayedScore = 0,
                    level = level,
                    didLevelUp = didLevelUp,
                    showLevelBadge = false,
                    leaderboard = result.leaderboard.map { entry ->
                        LeaderboardEntryUi(
                            rank = entry.rank,
                            userName = entry.userName,
                            score = entry.score,
                            isCurrentUser = entry.isCurrentUser,
                        )
                    },
                    isOnLeaderboard = result.isOnLeaderboard,
                    userRank = result.userRank,
                    headlineMessage = headline,
                    subMessage = sub,
                )
            }
            is FinalResult.Failure -> {
                _uiState.value = ResultUiState(
                    isLoading = false,
                    errorMessage = result.errorMessage,
                )
            }
        }
    }

    fun onAnimationsStarted() {
        val state = _uiState.value
        if (state.isLoading || state.errorMessage != null) return

        _uiState.value = state.copy(
            xpProgress = if (state.xpEarned > 0) {
                (state.xpEarned % XP_PER_LEVEL).toFloat() / XP_PER_LEVEL
            } else {
                0f
            }.coerceIn(0f, 1f),
            displayedScore = state.score,
            showLevelBadge = state.didLevelUp,
        )
    }

    private fun buildMessages(result: FinalResult.Success): Pair<String, String> {
        return if (result.isOnLeaderboard && result.userRank != null) {
            "You made it to the Top 10!" to
                "Amazing, ${result.user}! You're #${result.userRank} on the leaderboard."
        } else if (result.score == result.totalQuestions) {
            "Perfect score!" to
                "Incredible work, ${result.user}! Keep it up and climb the leaderboard next time."
        } else if (result.score > 0) {
            "Nice effort!" to
                "Good try, ${result.user}! Practice a bit more and you'll reach the Top 10."
        } else {
            "Keep going!" to
                "Don't give up, ${result.user}! Every quiz makes you stronger — try again."
        }
    }

    companion object {
        private const val XP_PER_CORRECT = 10
        private const val XP_PER_LEVEL = 100
    }
}
