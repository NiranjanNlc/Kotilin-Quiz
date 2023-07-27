package org.niranjan.quiz.result

import org.niranjan.quiz.modal.QuestionEntity

sealed class QuestionAnswerResult {
    data class Success(val question: QuestionEntity?, val isLastQuestion: Boolean) : QuestionAnswerResult()
    data class Failure(val errorMessage: String) : QuestionAnswerResult()
}