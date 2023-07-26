package com.example.kotlinquiz.ui.main.viewmodal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinquiz.ui.main.util.QuizPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.usecase.QuestionAnswerUseCase
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val useCase: QuestionAnswerUseCase,
    private val prefsHelper: QuizPrefsHelper
) : ViewModel() {

    private val _currentQuestion = MutableLiveData<QuestionEntity?>()
    val currentQuestion: LiveData<QuestionEntity?> get() = _currentQuestion

    private val _isLastQuestion = MutableLiveData<Boolean>()
    val isLastQuestion: LiveData<Boolean> get() = _isLastQuestion

    private val _isAnswered = MutableLiveData<Boolean>()
    val isAnswered: LiveData<Boolean> get() = _isAnswered

    private val _selectedOptionPosition = MutableLiveData<Int>()
    val selectedOptionPosition: LiveData<Int> get() = _selectedOptionPosition

    private var isCorrect: Boolean = false

    init {
        _isLastQuestion.value = false
        _selectedOptionPosition.value = 0
    }

    fun getFirstQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            val firstqs = useCase.getFirstQuestion()
            Log.i("startquiz", "getFirstQuestion: $firstqs")
            withContext(Dispatchers.Main) {
                _currentQuestion.value = firstqs.first
            }
        }
    }

    fun onOptionSelected(selectedOptionPosition: Int) {
        _selectedOptionPosition.value = selectedOptionPosition
    }

    fun checkAnswer(selectedOptionPosition: Int): Boolean {
     try {
         val currentQuestion = currentQuestion.value ?: return false
         val correctAnswerPosition = currentQuestion.answers.indexOfFirst { it.isCorrect }
         _isAnswered.value= true
         isCorrect = selectedOptionPosition == correctAnswerPosition
     }
     catch (e:Exception) {
         Log.i("startquiz", "checkAnswer: ${e.message}")
     }
        return isCorrect
    }


    fun moveToNextQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            val nextQuestion = useCase.answerQuestionAndGetNext(
                currentQuestion.value?.questionId ?: "",
                isCorrect
            )
            if (nextQuestion.second) {
                _isLastQuestion.postValue(true)
            }
            withContext(Dispatchers.Main) {
                _currentQuestion.value = nextQuestion.first
                _selectedOptionPosition.value = 0 // Reset selected option for the next question
                _isLastQuestion.value = nextQuestion.second!! // Check if it's the last question
                _isAnswered.value= false// Check if it's the last question
            }
        }
    }

}
