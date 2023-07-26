package com.example.kotlinquiz.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.niranjan.quiz.modal.AnswerEntity


@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val qsId: Int = 0 ,
    val questionId: String,
    val text: String=" ",
    val answers: List<AnswerEntity>,
    val difficultyLevel: Int,
    val category: String,
    val correctAnswer: AnswerEntity? = null,
    val isAnswered: Boolean = false,
)
    // Other properties representing question data
