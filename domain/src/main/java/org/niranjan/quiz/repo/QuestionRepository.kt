package org.niranjan.quiz.repo

import org.niranjan.quiz.modal.AnswerEntity
import org.niranjan.quiz.modal.QuestionEntity


interface QuestionRepository {
    fun createQuestion(question: QuestionEntity)
    fun updateQuestionAsAnswered(question: QuestionEntity)
    fun deleteQuestion(questionId: String)
    fun getQuestionById(questionId: String): QuestionEntity?
    fun putallQuestion(): List<QuestionEntity>
    fun getQuestionAnswers(questionId: String): List<AnswerEntity>
    fun getAllquestion(): List<QuestionEntity>
}