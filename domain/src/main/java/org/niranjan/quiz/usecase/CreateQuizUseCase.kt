package org.niranjan.quiz.usecase

import android.util.Log
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.modal.UserEntity
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.result.QuizResult
import java.util.UUID

class CreateQuizUseCase(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository
) {

    fun startQuiz(user: String): QuizResult  {
      try {
          val randomQuestions = getRandomQuizQuestions(5)
          Log.i("startquiz", "startQuiz: ${randomQuestions.size}")
          return if (randomQuestions.isNotEmpty()) {
              QuizResult.Success(createQuiz(user, randomQuestions))
          } else {
              Log.i("startquiz", "startQuiz: null")
              QuizResult.Failure("No questions found")
          }
      } catch (e: Exception) {
          return QuizResult.Failure(e.message ?: "Something went wrong")
      }
    }

    public fun createQuiz(user: String, randomQuestions: List<QuestionEntity>): QuizEntity {
        val quiz = QuizEntity(
            UUID.randomUUID().toString(),
            user,
            randomQuestions,
            emptyList(),
            System.currentTimeMillis(),
            1200000
        )
        return quizRepository.createQuiz(quiz)!!
    }

    public fun getRandomQuizQuestions( questionCount: Int): List<QuestionEntity> {
        val allQuestions = questionRepository.putallQuestion()
        return allQuestions.shuffled().take(questionCount)
    }


}
