package com.example.kotlinquiz.ui.main.viewmodal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinquiz.ui.main.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.niranjan.quiz.result.AnswerResult
import org.niranjan.quiz.result.FinalResult
import org.niranjan.quiz.usecase.SubmitQuizResultUseCase
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
   val  useCase: SubmitQuizResultUseCase
) : ViewModel() {
    
     private val _resultState  = MutableLiveData<ResultState>()
    val resultState: LiveData<ResultState> get() = _resultState

    init {
        getMyResult()
    }

    fun getMyResult(){
        val result = useCase.submitQuizResult()
        Log.i("ResultViewModel", "getMyResult: $result")
        when(result){
            is FinalResult.Success -> {
                _resultState.postValue(ResultState.Success(result.user, result.score))
            }
            is FinalResult.Failure -> {
                _resultState.postValue(ResultState.Failure(result.errorMessage))
            }
            else -> {
                _resultState.postValue(ResultState.Failure("Something went wrong"))
            }
        }
    }
}