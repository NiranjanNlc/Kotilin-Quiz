package com.example.kotlinquiz.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlinquiz.local.dao.QuestionDao
import com.example.kotlinquiz.local.dao.QuizDao
import com.example.kotlinquiz.local.dao.ScoreDao
import com.example.kotlinquiz.local.dao.UserDao
import com.example.kotlinquiz.local.entity.Answer
import com.example.kotlinquiz.local.entity.Question
import com.example.kotlinquiz.local.entity.Quiz
import com.example.kotlinquiz.util.MyCustomConverter

@Database(entities = [User::class,
                        Quiz::class,
                            Score::class,
                                Question::class,
                             Answer:: class], version = 9, exportSchema = false)
@TypeConverters(MyCustomConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun quizDao(): QuizDao
    abstract fun questionDao(): QuestionDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        private const val DATABASE_NAME = "my-database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries()
                .build()
                 INSTANCE = instance
                instance
            }
        }
    }
}
