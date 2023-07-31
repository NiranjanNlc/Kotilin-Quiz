package com.example.kotlinquiz.repository

import com.example.kotlinquiz.local.dao.QuizDao
import com.example.kotlinquiz.local.dao.UserDao
import com.example.kotlinquiz.local.entity.Quiz
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.modal.ScoreEntity
import org.niranjan.quiz.repo.QuizRepository

class QuizRepoImpl( private val quizDao: QuizDao) : QuizRepository {

    override fun createQuiz(quiz: QuizEntity): QuizEntity? {
        var quiz1 = Quiz(
            id = quiz.id,
            userId = quiz.userId,
            questions = quiz.questions,
            scores = quiz.scores,
            startTime = quiz.startTime,
            duration = quiz.duration,
            isFinished = quiz.isFinished
        )
        quizDao.createQuiz(quiz1)
        var savedquiz =quizDao.getQuizById(quiz.id)
        if ( savedquiz != null) {
            return QuizEntity(
                id = savedquiz.id,
                userId = savedquiz.userId,
                questions = savedquiz.questions,
                scores = savedquiz.scores,
                startTime = savedquiz.startTime,
                duration = savedquiz.duration,
                isFinished = savedquiz.isFinished)
        }
        return null
    }

    override fun updateQuiz(quiz: QuizEntity) {
        var quiz1 = Quiz(
            id = quiz.id,
            userId = quiz.userId,
            questions = quiz.questions,
            scores = quiz.scores,
            startTime = quiz.startTime,
            duration = quiz.duration,
            isFinished = quiz.isFinished
        )
        quizDao.updateQuiz(quiz1)
    }

    override fun deleteQuiz(quizId: String) {
        TODO("Not yet implemented")
    }

    override fun getQuizById(quizId: String): QuizEntity? {
        var quiz = quizDao.getQuizById(quizId)
        if ( quiz != null) {
            return QuizEntity(
                id = quiz.id,
                userId = quiz.userId,
                questions = quiz.questions,
                scores = quiz.scores,
                startTime = quiz.startTime,
                duration = quiz.duration,
                isFinished = quiz.isFinished)
        }
        return null
    }

    override fun getQuizzesByUser(userId: String): List<QuizEntity> {
        TODO("Not yet implemented")
    }

    override fun getQuizScores(quizId: String): List<ScoreEntity> {
        TODO("Not yet implemented")
    }

    override fun getCurrentQuiz(): QuizEntity? {
        val quiz = quizDao.getAllUnfineshedQuiz().firstOrNull()
        if ( quiz != null) {
            return QuizEntity(
                id = quiz.id,
                userId = quiz.userId,
                questions = quiz.questions,
                scores = quiz.scores,
                startTime = quiz.startTime,
                duration = quiz.duration,
                isFinished = quiz.isFinished)
        }
        return null
    }

    override fun getLastFinishedQuiz(): QuizEntity? {
        val quiz= quizDao.getAllfineshedQuiz().last()
        return QuizEntity(
            id = quiz.id,
            userId = quiz.userId,
            questions = quiz.questions,
            scores = quiz.scores,
            startTime = quiz.startTime,
            duration = quiz.duration,
            isFinished = quiz.isFinished)

    }
}