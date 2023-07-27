package org.niranjan.quiz.result

sealed class FinalResult {
     data class Success(val user: String, val score: Int) : FinalResult()
    data class Failure(val errorMessage: String) : FinalResult()
}