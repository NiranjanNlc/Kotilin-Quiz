package org.niranjan.quiz.repo

import org.niranjan.quiz.modal.AnswerEntity

interface AnswerRepository {
    fun createAnswer(answer: AnswerEntity)
    fun updateAnswer(answer: AnswerEntity)
    fun deleteAnswer(answerId: String)
    fun getAnswerById(answerId: String): AnswerEntity?
}