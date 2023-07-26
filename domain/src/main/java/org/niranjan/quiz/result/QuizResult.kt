package org.niranjan.quiz.result

import org.niranjan.quiz.modal.QuizEntity

 public sealed class QuizResult {
    data class Success(val quiz: QuizEntity) : QuizResult()
    data class Failure(val error: String) : QuizResult()
}