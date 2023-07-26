package org.niranjan.quiz.repo

import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.modal.ScoreEntity


interface QuizRepository {
    fun createQuiz(quiz: QuizEntity): QuizEntity?
    fun updateQuiz(quiz: QuizEntity)
    fun deleteQuiz(quizId: String)
    fun getQuizById(quizId: String): QuizEntity?
    fun getQuizzesByUser(userId: String): List<QuizEntity>
    fun getQuizScores(quizId: String): List<ScoreEntity>
    fun getCurrentQuiz(): QuizEntity?
    }