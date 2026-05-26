package com.example.kotlinquiz.ui.main.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.ui.compose.quiz.AnswerFeedback
import com.example.kotlinquiz.ui.compose.quiz.AnswerUiModel
import com.example.kotlinquiz.ui.compose.quiz.QuizEvent
import com.example.kotlinquiz.ui.compose.quiz.QuizUiState
import com.example.kotlinquiz.ui.main.util.QuizPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.result.AnswerResult
import org.niranjan.quiz.usecase.QuestionAnswerUseCase
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuestionAnswerUseCase,
    private val prefsHelper: QuizPrefsHelper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<QuizEvent>()
    val events: SharedFlow<QuizEvent> = _events.asSharedFlow()

    private var currentQuestion: QuestionEntity? = null
    private var answeredCount = 0
    private var totalQuestions = 0
    private var hearts = MAX_HEARTS
    private var streak = 0

    init {
        getFirstQuestion()
    }

    fun getFirstQuestion() {
        val quizId = prefsHelper.getQuizId()
        if (quizId.isNullOrBlank()) {
            _uiState.value = QuizUiState(
                isLoading = false,
                errorMessage = "Quiz not found",
            )
            return
        }

        _uiState.value = QuizUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            totalQuestions = useCase.getQuizQuestionCount(quizId)
            handleResult(useCase.getFirstQuestion(quizId))
        }
    }

    fun onAnswerTapped(answerId: String) {
        val state = _uiState.value
        if (state.isInputLocked || state.isLoading || state.errorMessage != null) return

        val question = currentQuestion ?: return
        val quizId = prefsHelper.getQuizId() ?: return

        val isCorrect = answerId == question.correctAnswer?.id
        val feedback = if (isCorrect) AnswerFeedback.CORRECT else AnswerFeedback.INCORRECT

        if (!isCorrect) {
            hearts = (hearts - 1).coerceAtLeast(0)
            streak = 0
        } else {
            streak += 1
        }

        _uiState.value = state.copy(
            selectedAnswerId = answerId,
            feedback = feedback,
            feedbackMessage = if (isCorrect) {
                "Correct!"
            } else {
                "Wrong answer"
            },
            isInputLocked = true,
            hearts = hearts,
            streak = streak,
        )

        viewModelScope.launch(Dispatchers.IO) {
            useCase.recordAnswer(quizId, question, isCorrect)
        }

        viewModelScope.launch {
            delay(FEEDBACK_DELAY_MS)
            answeredCount++
            if (hearts <= 0) {
                _events.emit(QuizEvent.NavigateToResults)
            } else {
                advanceToNextQuestionInternal(quizId)
            }
        }
    }

    fun onQuitConfirmed() {
        prefsHelper.saveQuizStatus(false, "")
        viewModelScope.launch {
            _events.emit(QuizEvent.NavigateToWelcome)
        }
    }

    private fun advanceToNextQuestionInternal(quizId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.advanceToNextQuestion(quizId)
            when (result) {
                is AnswerResult.Success -> {
                    if (result.isLastQuestion) {
                        _events.emit(QuizEvent.NavigateToResults)
                    } else {
                        handleResult(result)
                    }
                }
                is AnswerResult.Failure -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isInputLocked = false,
                        errorMessage = result.errorMessage,
                    )
                }
            }
        }
    }

    private fun handleResult(result: AnswerResult) {
        when (result) {
            is AnswerResult.Success -> {
                if (result.isLastQuestion && result.question == null) {
                    viewModelScope.launch {
                        _events.emit(QuizEvent.NavigateToResults)
                    }
                    return
                }
                result.question?.let { question ->
                    currentQuestion = question
                    _uiState.value = buildActiveState(question)
                }
            }
            is AnswerResult.Failure -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = result.errorMessage,
                )
            }
        }
    }

    private fun buildActiveState(question: QuestionEntity): QuizUiState {
        return QuizUiState(
            isLoading = false,
            questionText = question.text,
            answers = question.answers.map { AnswerUiModel(it.id, it.text) },
            correctAnswerId = question.correctAnswer?.id,
            currentQuestionIndex = answeredCount + 1,
            totalQuestions = totalQuestions,
            hearts = hearts,
            streak = streak,
            feedback = null,
            feedbackMessage = null,
            selectedAnswerId = null,
            isInputLocked = false,
        )
    }

    companion object {
        private const val MAX_HEARTS = 3
        private const val FEEDBACK_DELAY_MS = 3500L
    }
}
