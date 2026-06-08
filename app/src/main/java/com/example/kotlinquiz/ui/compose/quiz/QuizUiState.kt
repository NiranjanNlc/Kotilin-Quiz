package com.example.kotlinquiz.ui.compose.quiz

data class QuizUiState(
    val isLoading: Boolean = true,
    val questionText: String = "",
    val answers: List<AnswerUiModel> = emptyList(),
    val correctAnswerId: String? = null,
    val currentQuestionIndex: Int = 0,
    val totalQuestions: Int = 0,
    val hearts: Int = 3,
    val streak: Int = 0,
    val feedback: AnswerFeedback? = null,
    val feedbackMessage: String? = null,
    val selectedAnswerId: String? = null,
    val isInputLocked: Boolean = false,
    val errorMessage: String? = null,
)

data class AnswerUiModel(
    val id: String,
    val text: String,
)

enum class AnswerFeedback {
    CORRECT,
    INCORRECT,
}

sealed class QuizEvent {
    data object NavigateToResults : QuizEvent()
    data object NavigateToWelcome : QuizEvent()
}
