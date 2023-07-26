package org.niranjan.quiz.usecase

import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository

class AnswerQuizQuestionUseCase(
    private val quizRepository: QuizRepository,
    private val questionRepository: QuestionRepository,
    private val scoreRepository: ScoreRepository
) {
    fun answerQuestion(quizId: String, questionId: String, answer: String): Boolean {
        val quiz = quizRepository.getQuizById(quizId)
        if (quiz != null && quiz.isFinished.not()) {
            val question = quiz.questions.find { it.questionId == questionId }
            if (question != null && !question.isAnswered) {
                val isCorrect = (question.correctAnswer?.text == answer)
                val answeredQuestion = question.copy(isAnswered = true)
                questionRepository.updateQuestionAsAnswered(answeredQuestion)
                if (isCorrect)
                {
                    scoreRepository.getScoresByQuiz(quizId).forEach {
                        val score = it.copy(score = it.score + 1)
                        scoreRepository.saveScore(score)
                    }
                }
                return isCorrect
            }
        }
        return false
    }
}
