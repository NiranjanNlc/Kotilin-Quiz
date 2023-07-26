package org.niranjan.quiz.usecase

import android.util.Log
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository

class QuestionAnswerUseCase(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository,
    private val scoreRepository: ScoreRepository
) {
    fun getFirstQuestion(): Pair<QuestionEntity?, Boolean> {
        Log.i("startquiz", "getFirstQuestion: going one ")
        return getNextQuestion()
    }

    fun answerQuestionAndGetNext(
        questionId: String ,
        correctness: Boolean
    ): Pair<QuestionEntity?, Boolean> {
        Log.i("startquiz", "answerQuestionAndGetNext: $questionId $correctness")
        val quiz = quizRepository.getCurrentQuiz()
        Log.i("startquiz", "answerQuestionAndGetNext: $quiz")
        val question = quiz?.questions?.find { it.questionId == questionId }
        if ((question != null) && !question.isAnswered) {
            val answeredQuestion = question.copy(isAnswered = true)
            Log.i("startquiz", "answerQuestionAndGetNext: $answeredQuestion")
            try {
                questionRepository.updateQuestionAsAnswered(answeredQuestion)
            } catch (e: Exception) {
                Log.i("startquiz", "answerQuestionAndGetNext: ${e.message}")
            }
        }
        if (correctness) {
            if (quiz != null) {
                updateScoreForQuiz(quiz.id)
            }
        }
        return getNextQuestion()
    }
    private fun updateScoreForQuiz(quizId: String) {
       try {
           val quizScores = scoreRepository.getScoresByQuiz(quizId)
           for (score in quizScores) {
               val newScoreValue = score.score + 1
               scoreRepository.saveScore(score.copy(score = newScoreValue))
           }
       }
       catch (e:Exception)
       {
           Log.i("startquiz", "updateScoreForQuiz: ${e.message}")
       }
    }

    fun getNextQuestion(): Pair<QuestionEntity?, Boolean> {
        val quiz = quizRepository.getCurrentQuiz()
        val unansweredQuestions = quiz?.questions?.filter { !it.isAnswered } ?: emptyList()
        val answeredQuestions = quiz?.questions?.filter { it.isAnswered } ?: emptyList()
        val listOfAnswered = quiz?.questions?.filter { it.isAnswered }
        val listOfUnAnswered = quiz?.questions?.filter { !it.isAnswered }
        listOfAnswered?.forEach {
            Log.i("startquiz", "getNextQuestion: ${it.text}")
        }
        listOfUnAnswered?.forEach {
            Log.i("startquiz inanswered ", "getNextQuestion: ${it.text}")
        }
        Log.i("startquiz answered ", "getNextQuestion: $unansweredQuestions")
        if (unansweredQuestions.isEmpty()) {
            // Notify that it's the last question
            val isLastQuestion = answeredQuestions.size == (quiz?.questions?.size ?: 0)
            return Pair(null, isLastQuestion)
        } else {
            val nextQuestion = unansweredQuestions.random()
            return Pair(nextQuestion, false)
        }
    }

}
