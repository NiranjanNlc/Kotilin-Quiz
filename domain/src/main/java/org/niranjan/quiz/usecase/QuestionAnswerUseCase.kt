package org.niranjan.quiz.usecase

import android.util.Log
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.result.AnswerResult


class QuestionAnswerUseCase(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository,
    private val scoreRepository: ScoreRepository
) {

    fun getQuizQuestionCount(quizId: String): Int =
        quizRepository.getQuizById(quizId)?.questions?.size ?: 0

    fun getFirstQuestion(quizId: String): AnswerResult {
        Log.i(TAG, "getFirstQuestion: quizId=$quizId")
        if (quizRepository.getQuizById(quizId) == null) {
            return AnswerResult.Failure("Quiz not found")
        }
        return getNextQuestion(quizId)
    }

    fun recordAnswer(quizId: String, question: QuestionEntity, correctness: Boolean) {
        val quiz = quizRepository.getQuizById(quizId) ?: return
        val alreadyAnswered = quiz.questions.any {
            it.questionId == question.questionId && it.isAnswered
        }
        if (alreadyAnswered) return

        Log.i(TAG, "recordAnswer: ${question.questionId}, correct=$correctness")
        markQuestionAsAnswered(question.questionId, quizId)
        if (correctness) {
            updateScoreForQuiz(quizId)
        }
    }

    fun advanceToNextQuestion(quizId: String): AnswerResult {
        return getNextQuestion(quizId)
    }

    private fun markQuestionAsAnswered(questionId: String, quizId: String) {
        val quiz = quizRepository.getQuizById(quizId) ?: return
        val updatedQuestions = quiz.questions.map { question ->
            if (question.questionId == questionId) {
                question.copy(isAnswered = true)
            } else {
                question
            }
        }
        quizRepository.updateQuiz(quiz.copy(questions = updatedQuestions))
    }

    private fun updateScoreForQuiz(quizId: String) {
        val quizScores = scoreRepository.getScoresByQuiz(quizId)
        Log.i("result", "updateScoreForQuiz: ${quizScores.sumOf { it.score }}")
        for (score in quizScores) {
            scoreRepository.updateScore(score.copy(score = score.score + 1))
        }
    }

    private fun getNextQuestion(quizId: String): AnswerResult {
        val quiz = quizRepository.getQuizById(quizId)
            ?: return AnswerResult.Failure("Quiz not found")

        val unansweredQuestions = quiz.questions.filter { !it.isAnswered }
        val answeredQuestions = quiz.questions.filter { it.isAnswered }

        listOfAnsweredQuestions(answeredQuestions)
        listOfUnansweredQuestions(unansweredQuestions)

        if (unansweredQuestions.isEmpty()) {
            quizRepository.updateQuiz(quiz.copy(isFinished = true))
            Log.i(TAG, "getNextQuestion: quiz finished, answered=${answeredQuestions.size}")
            return AnswerResult.Success(null, isLastQuestion = true)
        }

        val nextQuestion = unansweredQuestions.first()
        return AnswerResult.Success(nextQuestion, false)
    }

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
