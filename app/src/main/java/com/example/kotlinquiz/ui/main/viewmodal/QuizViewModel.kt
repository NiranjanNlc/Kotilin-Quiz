package com.example.kotlinquiz.ui.main.viewmodal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.ui.main.util.AnswerState
import com.example.kotlinquiz.ui.main.util.QuizPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.result.AnswerResult
import org.niranjan.quiz.usecase.QuestionAnswerUseCase
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuestionAnswerUseCase,
    private val prefsHelper: QuizPrefsHelper
) : ViewModel() {

    private val _selectedAnswerId = MutableLiveData<String?>()
    val selectedAnswerId: LiveData<String?> get() = _selectedAnswerId

    private val _answerState = MutableLiveData<AnswerState>()
    val answerState: LiveData<AnswerState> get() = _answerState

    init {
        _selectedAnswerId.value = null
    }

    fun getFirstQuestion() {
        val quizId = prefsHelper.getQuizId()
        if (quizId.isNullOrBlank()) {
            _answerState.postValue(AnswerState.Failure("Quiz not found"))
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.getFirstQuestion(quizId)
            Log.i("startquiz", "getFirstQuestion: $result")
            hadleResult(result)
        }
    }

    private fun hadleResult(result: AnswerResult) {
        when (result) {
            is AnswerResult.Success -> {
                try {
                    Log.i("startquiz", "your result  : $result")
                    _answerState.postValue(
                        AnswerState.Success(
                            result.question,
                            result.isLastQuestion
                        )
                    )
                    _selectedAnswerId.postValue(null)
                } catch (e: Exception) {
                    Log.i("startquiz", "getFirstQuestion: $e")
                }
            }

            is AnswerResult.Failure -> {
                _answerState.postValue(AnswerState.Failure(result.errorMessage))
            }

            else -> {
                Log.i("startquiz", "what he hell : $result")
            }
        }
    }

    fun onOptionSelected(answerId: String) {
        _selectedAnswerId.value = answerId
    }

    fun submitAnswer(question: QuestionEntity, isCorrect: Boolean) {
        val quizId = prefsHelper.getQuizId() ?: return
        viewModelScope.launch(Dispatchers.IO) {
            useCase.recordAnswer(quizId, question, isCorrect)
        }
    }

    fun advanceToNextQuestion() {
        val quizId = prefsHelper.getQuizId()
        if (quizId.isNullOrBlank()) {
            _answerState.postValue(AnswerState.Failure("Quiz not found"))
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.advanceToNextQuestion(quizId)
            hadleResult(result)
        }
    }

    fun resetStaet() {
        _answerState.value = AnswerState.NotAnswered
    }
}
