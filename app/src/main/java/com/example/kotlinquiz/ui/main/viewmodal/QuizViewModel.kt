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


    private val _selectedOptionPosition = MutableLiveData<Int>()
    val selectedOptionPosition: LiveData<Int> get() = _selectedOptionPosition


    private val _answerState  = MutableLiveData<AnswerState>()
    val answerState: LiveData<AnswerState> get() = _answerState

    init {
        _selectedOptionPosition.value = 0
    }

    fun getFirstQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.getFirstQuestion()
            Log.i("startquiz", "getFirstQuestion: $result")
            Log.i("startquiz", result.toString())
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
                    _selectedOptionPosition.postValue(0)
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

    fun onOptionSelected(selectedOptionPosition: Int) {
        _selectedOptionPosition.value = selectedOptionPosition
    }




    fun moveToNextQuestion(currentQuestionId: QuestionEntity, isCorrect: Boolean) {
        Log.i("result + ", isCorrect.toString())
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase.answerQuestionAndGetNext(
                currentQuestionId,
                isCorrect
            )
            hadleResult(result)
        }
    }

    fun resetStaet() {
        _answerState.value = AnswerState.NotAnswered
    }

}
