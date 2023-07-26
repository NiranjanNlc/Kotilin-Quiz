package org.niranjan.quiz.repo

import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.modal.ScoreEntity
import org.niranjan.quiz.modal.UserEntity

interface UserRepository {
    fun registerUser(user: UserEntity)
    fun loginUser(username: String, password: String): UserEntity?
    fun updateUser(user: UserEntity)
    fun deleteUser(user: UserEntity)
    fun getUserById(userId: String): UserEntity?
    fun getUserQuizzes(userId: String): List<QuizEntity>
    fun getUserScores(userId: String): List<ScoreEntity>
}