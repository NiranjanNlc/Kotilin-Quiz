package com.example.kotlinquiz.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class Answer(
    @PrimaryKey
    val id: String,
    val questionId: String,
    val text: String,
    val isCorrect: Boolean
)
