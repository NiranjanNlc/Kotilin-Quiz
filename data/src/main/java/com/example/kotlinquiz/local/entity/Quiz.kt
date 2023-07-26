package com.example.kotlinquiz.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.ScoreEntity

@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey
    val id: String,
    val userId: String,
    val questions: List<QuestionEntity>,
    val scores: List<ScoreEntity>,
    val startTime: Long,
    val duration: Long,
    val isFinished: Boolean = false
)