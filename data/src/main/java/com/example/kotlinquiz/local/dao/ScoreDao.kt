package com.example.kotlinquiz.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlinquiz.local.Score


@Dao
interface ScoreDao {
    @Insert
    fun saveScore(score : Score)

    @Query("SELECT * FROM scores WHERE quizId = :quizId AND userId = :userId")
    fun getScoreByQuizAndUser(quizId: String, userId: String): Score?

    @Query("SELECT * FROM scores WHERE quizId = :quizId")
    fun getScoresByQuiz(quizId: String): List<Score>

    @Query("SELECT * FROM scores WHERE userId = :userId")
    fun getScoresByUser(userId: String): List<Score>
}
