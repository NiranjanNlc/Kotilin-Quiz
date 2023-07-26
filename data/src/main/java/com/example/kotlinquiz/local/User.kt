package com.example.kotlinquiz.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String,
    val username: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String
    // Other properties representing user data
)