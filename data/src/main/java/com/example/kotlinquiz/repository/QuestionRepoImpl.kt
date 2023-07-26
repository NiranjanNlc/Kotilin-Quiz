package com.example.kotlinquiz.repository

import com.example.kotlinquiz.local.entity.Question
import com.example.kotlinquiz.local.dao.QuestionDao
import com.example.kotlinquiz.util.readJsonFileFromAssets
import org.json.JSONObject
import org.niranjan.quiz.modal.AnswerEntity
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.repo.QuestionRepository
import android.content.Context
import android.util.Log
import javax.inject.Inject

class QuestionRepoImpl @Inject constructor(private val questionDao: QuestionDao,
                                           private val context: Context): QuestionRepository
{
    override fun createQuestion(question: QuestionEntity) {
         questionDao.createQuestion(
                Question(
                   questionId= question.questionId,
                    text = question.text,
                     answers = question.answers,
                    difficultyLevel = question.difficultyLevel,
                    category = question.category,
                    correctAnswer = question.correctAnswer,
                    isAnswered = question.isAnswered
                )
         )
    }

    override fun updateQuestionAsAnswered(question: QuestionEntity) {
        questionDao.getQuestionById(question.questionId).let {
            if (it != null) {
                questionDao.updateQuestion( it.copy(isAnswered = false))
            }    
        }
        
    }

    override fun deleteQuestion(questionId: String) {
        questionDao.getQuestionById(questionId).let {
            if (it != null) {
                questionDao.deleteQuestion(it)
            }    
        }
        
    }

    override fun getQuestionById(questionId: String): QuestionEntity? {
         return questionDao.getQuestionById(questionId)?.let {
             QuestionEntity(
                 it.questionId,
                 it.text,
                 it.answers,
                 it.difficultyLevel,
                 it.category,
                 it.correctAnswer,
                 it.isAnswered
             )
         }
    }
    override fun getAllQuestions(): List<QuestionEntity> {
            val jsonString =   readJsonFileFromAssets(this.context)
            Log.i(" string ", jsonString)
            val json = JSONObject(jsonString)
            val questionsArray = json.getJSONArray("questions")
            val questionsList = mutableListOf<Question>()

            for (i in 0 until questionsArray.length()) {
                val questionJson = questionsArray.getJSONObject(i)
                val questionId = questionJson.getString("questionId")
                val text = questionJson.getString("text")
                val difficultyLevel = questionJson.getInt("difficultyLevel")
                val category = questionJson.getString("category")
                val isAnswered = questionJson.getBoolean("isAnswered")
                val correctAnswerJson = questionJson.optJSONObject("correctAnswer")
                val correctAnswer = if (correctAnswerJson != null) {
                    AnswerEntity(
                        correctAnswerJson.getString("id"),
                        correctAnswerJson.getString("questionId"),
                        correctAnswerJson.getString("text"),
                        correctAnswerJson.getBoolean("isCorrect")
                    )
                } else {
                    null
                }
                val answersArray = questionJson.getJSONArray("answers")
                val answersList = mutableListOf<AnswerEntity>()

                for (j in 0 until answersArray.length()) {
                    val answerJson = answersArray.getJSONObject(j)
                    val answer = AnswerEntity(
                        answerJson.getString("id"),
                        answerJson.getString("questionId"),
                        answerJson.getString("text"),
                        answerJson.getBoolean("isCorrect")
                    )
                    answersList.add(answer)
                }

                val question = Question(
                    questionId= questionId,
                    text = text,
                    answers = answersList,
                    difficultyLevel = difficultyLevel,
                    category = category,
                    correctAnswer = correctAnswer,
                    isAnswered = isAnswered
                )
                // Save the questions to the Room database
                questionDao.createQuestion(question)
                questionsList.add(question)
            }
        return questionDao.getAllQuestion().map {
            QuestionEntity(
                it.questionId,
                it.text,
                it.answers,
                it.difficultyLevel,
                it.category,
                it.correctAnswer,
                it.isAnswered
            )
        }
    }

    override fun getQuestionAnswers(questionId: String): List<AnswerEntity> {
        questionDao.getQuestionById(questionId).let {
            if (it != null) {
                return it.answers.map { answer ->
                    AnswerEntity(
                        answer.id,
                        answer.questionId,
                        answer.text,
                        answer.isCorrect
                    )
                }
            }
        }
        return emptyList()
    }
}