package com.example.kotlinquiz.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey val scoreId: String,
    val userId: String,
    val quizId: String,
    val score: Int,
    // Other properties representing score data
)
