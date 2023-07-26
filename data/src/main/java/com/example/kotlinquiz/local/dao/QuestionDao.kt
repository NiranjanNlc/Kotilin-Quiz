package com.example.kotlinquiz.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.kotlinquiz.local.entity.Answer
import com.example.kotlinquiz.local.entity.Question


@Dao
interface QuestionDao {
    @Insert
    fun createQuestion(question: Question)

    @Update
    fun updateQuestion(question: Question)

    @Delete
    fun deleteQuestion(question: Question)

    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    fun getQuestionById(questionId: String): Question?

    @Query("SELECT * FROM questions  ")
    fun getAllQuestion( ): List<Question>

    @Query("SELECT * FROM answers WHERE questionId = :questionId")
    fun getQuestionAnswers(questionId: String): List<Answer>
}
