package org.niranjan.quiz.usecase

import android.util.Log
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.result.AnswerResult


class QuestionAnswerUseCase(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository,
    private val scoreRepository: ScoreRepository
) {

    fun getFirstQuestion(): AnswerResult {
        Log.i(TAG, "getFirstQuestion: Going to the first question.")
        return getNextQuestion()
    }

    fun answerQuestionAndGetNext(question:QuestionEntity, correctness: Boolean): AnswerResult {
        Log.i(TAG, "answerQuestionAndGetNext: Question ID: ${question.questionId}, Correctness: $correctness")
        val quiz = quizRepository.getCurrentQuiz()
        Log.i(TAG, "answerQuestionAndGetNext: Current Quiz: $quiz")
        Log.i(TAG, "answerQuestionAndGetNext: Question is already not  answered.")
        markQuestionAsAnswered(question)
        if (correctness) {
            try {
                if (quiz != null) {
                    updateScoreForQuiz(quiz.id)
                }
            } catch (e: Exception) {
                return AnswerResult.Failure("Failed to update score: ${e.message}")
            }
    }
        return getNextQuestion()
    }

    private fun markQuestionAsAnswered(answeredQuestion: QuestionEntity) {
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

    private fun getNextQuestion(): AnswerResult {
        val questions = questionRepository.getAllquestion()
        questions.forEach{
            Log.i(TAG, "getNextQuestion: Question: ${it.text} snd ${it.isAnswered}")
        }
        val unansweredQuestions = questions.filter { !it.isAnswered } ?: emptyList()
        val answeredQuestions = questions.filter { it.isAnswered } ?: emptyList()

        listOfAnsweredQuestions(answeredQuestions)
        listOfUnansweredQuestions(unansweredQuestions)

        if (unansweredQuestions.isEmpty()) {
            // Notify that it's the last question
            val isLastQuestion = answeredQuestions.size == (questions.size ?: 0)
            Log.i(TAG, "getNextQuestion: isLastQuestion: $isLastQuestion")
            return AnswerResult.Success(null, isLastQuestion)
        } else {
            val nextQuestion = unansweredQuestions.random()
            return AnswerResult.Success(nextQuestion, false)
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
