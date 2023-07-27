package org.niranjan.quiz.usecase

import android.util.Log
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.result.QuestionAnswerResult


class QuestionAnswerUseCase(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository,
    private val scoreRepository: ScoreRepository
) {

    fun getFirstQuestion(): QuestionAnswerResult {
        Log.i(TAG, "getFirstQuestion: Going to the first question.")
        return getNextQuestion()
    }

    fun answerQuestionAndGetNext(questionId: String, correctness: Boolean): QuestionAnswerResult {
        Log.i(TAG, "answerQuestionAndGetNext: Question ID: $questionId, Correctness: $correctness")
        val quiz = quizRepository.getCurrentQuiz()
        Log.i(TAG, "answerQuestionAndGetNext: Current Quiz: $quiz")

        val question = quiz?.questions?.find { it.questionId == questionId }
        if (question != null && !question.isAnswered) {
            Log.i(TAG, "answerQuestionAndGetNext: Question is already not  answered.")
            markQuestionAsAnswered(question)
            if (correctness) {
                try {
                  updateScoreForQuiz(quiz.id)
                } catch (e: Exception) {
                    return QuestionAnswerResult.Failure("Failed to update score: ${e.message}")
                }
        }
        else
        {
            Log.i(TAG, "answerQuestionAndGetNext: Question is already answered.")
            return QuestionAnswerResult.Failure("Question is already answered or not present ")
        }
    }

        return getNextQuestion()
    }

    private fun markQuestionAsAnswered(question: QuestionEntity) {
        val answeredQuestion = question.copy(isAnswered = true)
        Log.i(TAG, "markQuestionAsAnswered: Marked as answered: $answeredQuestion")
        try {
            questionRepository.updateQuestionAsAnswered(answeredQuestion)
        } catch (e: Exception) {
            throw Exception("Failed to update question: ${e.message}")
        }
    }

    private fun updateScoreForQuiz(quizId: String) {
        val quizScores = scoreRepository.getScoresByQuiz(quizId)
        for (score in quizScores) {
            val newScoreValue = score.score + 1
            scoreRepository.saveScore(score.copy(score = newScoreValue))
        }
    }

    private fun getNextQuestion(): QuestionAnswerResult {
        val questions = questionRepository.getAllquestion()
        val unansweredQuestions = questions.filter { !it.isAnswered } ?: emptyList()
        val answeredQuestions = questions.filter { it.isAnswered } ?: emptyList()

        listOfAnsweredQuestions(answeredQuestions)
        listOfUnansweredQuestions(unansweredQuestions)

        if (unansweredQuestions.isEmpty()) {
            // Notify that it's the last question
            val isLastQuestion = answeredQuestions.size == (questions?.size ?: 0)
            return QuestionAnswerResult.Success(null, isLastQuestion)
        } else {
            val nextQuestion = unansweredQuestions.random()
            return QuestionAnswerResult.Success(nextQuestion, false)
        }
    }

// Rest of the code remains the same...

    private fun listOfAnsweredQuestions(answeredQuestions: List<QuestionEntity>?) {
        answeredQuestions?.forEach {
            Log.i(TAG, "getNextQuestion: Answered Question: ${it.text}")
        }
    }

    private fun listOfUnansweredQuestions(unansweredQuestions: List<QuestionEntity>?) {
        unansweredQuestions?.forEach {
            Log.i(TAG, "getNextQuestion: Unanswered Question: ${it.text}")
        }
    }

    companion object {
        private const val TAG = "startquiz"
    }
}
