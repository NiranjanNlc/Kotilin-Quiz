package com.example.kotlinquiz.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.kotlinquiz.local.Score
import com.example.kotlinquiz.local.User
import com.example.kotlinquiz.local.entity.Quiz


@Dao
interface UserDao {
    @Insert
    fun registerUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun loginUser(username: String, password: String): User?

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: String): User?

    @Query("SELECT * FROM quizzes WHERE userId = :userId")
    fun getUserQuizzes(userId: String): List<Quiz>

    @Query("SELECT * FROM scores WHERE userId = :userId")
    fun getUserScores(userId: String): List<Score>
}