package org.niranjan.quiz.result

import org.niranjan.quiz.modal.QuestionEntity

sealed class AnswerResult {
    data class Success(val question: QuestionEntity?, val isLastQuestion: Boolean) : AnswerResult()
    data class Failure(val errorMessage: String) : AnswerResult()
}