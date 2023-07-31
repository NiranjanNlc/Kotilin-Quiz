package com.example.kotlinquiz.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.kotlinquiz.local.Score
import com.example.kotlinquiz.local.entity.Quiz


@Dao
interface QuizDao {
    @Insert
    fun createQuiz(quiz: Quiz)

    @Update
    fun updateQuiz(quiz: Quiz)

    @Delete
    fun deleteQuiz(quiz: Quiz)

    @Query("SELECT * FROM quizzes WHERE id = :quizId")
    fun getQuizById(quizId: String): Quiz?

    @Query("SELECT * FROM quizzes WHERE userId = :userId")
    fun getQuizzesByUser(userId: String): List<Quiz>

    @Query("SELECT * FROM scores WHERE quizId = :quizId")
    fun getQuizScores(quizId: String): List<Score>
    @Query("SELECT * FROM quizzes where isFinished = 0 ")
    fun getAllUnfineshedQuiz(): List<Quiz>
    @Query("SELECT * FROM quizzes where isFinished = 1 ")
    fun getAllfineshedQuiz(): List<Quiz>
}
