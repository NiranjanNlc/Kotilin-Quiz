package com.example.kotlinquiz.ui.main.viewmodal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.ui.main.util.QuizState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.result.QuizResult
import org.niranjan.quiz.usecase.CreateQuizUseCase
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCase: CreateQuizUseCase
) : ViewModel()
{
    private val _quizState = MutableLiveData<QuizState>()
    val quizState: LiveData<QuizState> get() = _quizState
    fun startQuiz(username: String) {
        Log.i("startquiz", username)
            viewModelScope.launch(Dispatchers.IO) {
                _quizState.postValue(QuizState.Loading)
                val result = useCase.startQuiz(username)
                Log.i("startquiz", result.toString())
                _quizState.postValue(handleQuizResult(result))
            }
        }

    private fun handleQuizResult(result: QuizResult): QuizState {
        Log.i("startquiz", result.toString())
            return when (result) {
                is QuizResult.Success -> {
//                    _started.value = true
                    QuizState.Success(result.quiz)
                }
                is QuizResult.Failure -> QuizState.Failure(result.error)
            }
        }
}
