package org.niranjan.quiz

import android.util.Log
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.niranjan.quiz.modal.AnswerEntity
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.modal.UserEntity
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.result.QuizResult
import org.niranjan.quiz.usecase.CreateQuizUseCase


@RunWith(MockitoJUnitRunner::class)
class CreateQuizUseCaseTest {

    @Mock
    private lateinit var quizRepository: QuizRepository

    @Mock
    private lateinit var questionRepository: QuestionRepository

    private lateinit var createQuizUseCase: CreateQuizUseCase

    private lateinit var questions :List<QuestionEntity>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        createQuizUseCase = CreateQuizUseCase(quizRepository, questionRepository)

        val fakeAnswer1 = AnswerEntity("1", "Option 1", "",false)
        val fakeAnswer2 = AnswerEntity("2", "Option 2","", true)
        val fakeAnswer3 = AnswerEntity("3", "Option 3", "",false)

        val fakeQuestion1 = QuestionEntity(
            questionId = "1",
            text = "What is the capital of France?",
            answers = listOf(fakeAnswer1, fakeAnswer2, fakeAnswer3),
            difficultyLevel = 2,
            category = "Geography",
            correctAnswer = fakeAnswer2,
            isAnswered = false
        )

        val fakeQuestion2 = QuestionEntity(
            questionId = "2",
            text = "What is the largest planet in our solar system?",
            answers = listOf(fakeAnswer1, fakeAnswer2, fakeAnswer3),
            difficultyLevel = 1,
            category = "Science",
            correctAnswer = fakeAnswer3,
            isAnswered = false
        )

        val fakeQuestion3 = QuestionEntity(
            questionId = "3",
            text = "Who painted the Mona Lisa?",
            answers = listOf(fakeAnswer1, fakeAnswer2, fakeAnswer3),
            difficultyLevel = 2,
            category = "Art",
            correctAnswer = fakeAnswer1,
            isAnswered = false
        )

        val fakeQuestion4 = QuestionEntity(
            questionId = "4",
            text = "What is the symbol for the chemical element iron?",
            answers = listOf(fakeAnswer1, fakeAnswer2, fakeAnswer3),
            difficultyLevel = 1,
            category = "Chemistry",
            correctAnswer = fakeAnswer2,
            isAnswered = false
        )

        val fakeQuestion5 = QuestionEntity(
            questionId = "5",
            text = "What year was the first iPhone released?",
            answers = listOf(fakeAnswer1, fakeAnswer2, fakeAnswer3),
            difficultyLevel = 2,
            category = "Technology",
            correctAnswer = fakeAnswer3,
            isAnswered = false
        )
      questions = listOf(fakeQuestion1, fakeQuestion2, fakeQuestion3, fakeQuestion4, fakeQuestion5)
    }

    @Test
    fun `test startQuiz with valid user and questions`() {
        val user = "JohnDoe"

        `when`(questionRepository.getAllQuestions()).thenReturn(questions)
         `when`(quizRepository.createQuiz(any())).thenReturn(QuizEntity("1",
             user,
             questions,
             emptyList(),
             0,
             0,
             false))
        val quizEntity = createQuizUseCase.startQuiz(user)
        Log.i("QuizEntity", quizEntity.toString())
        assertNotNull(quizEntity)
        if (quizEntity is QuizResult.Success) {
            assertEquals(user, quizEntity.quiz.userId)
            assertEquals(5, quizEntity.quiz.questions.size)
        }
    }

    @Test
    fun `test startQuiz with valid user and no questions`() {
        val user = "JohnDoe"
        `when`(questionRepository.getAllQuestions()).thenReturn(emptyList())

        val quizEntity = createQuizUseCase.startQuiz(user)

        assert(quizEntity is QuizResult.Failure)
    }

    @Test
    fun `test startQuiz with empty user`() {
        val user = ""
        `when`(questionRepository.getAllQuestions()).thenReturn(emptyList())

        val quizEntity = createQuizUseCase.startQuiz(user)

        assert(quizEntity is QuizResult.Failure)
    }

    @Test
    fun `test createQuiz with valid user and random questions`() {
        val user = UserEntity(username = "JohnDoe")
         `when`(quizRepository.createQuiz(any())).thenReturn(QuizEntity("1",
            "JohnDoe",
            questions,
            emptyList(),
            0,
            0,
            false))
        val quizEntity = createQuizUseCase.createQuiz(user, questions)

        assertNotNull(quizEntity)
        assertEquals(user.username, quizEntity.userId)
        assertEquals(questions, quizEntity.questions)
    }

    @Test
    fun `test getRandomQuizQuestions with more questions requested than available`() {
        val questionCount = 10

        `when`(questionRepository.getAllQuestions()).thenReturn(questions)

        val randomQuestions = createQuizUseCase.getRandomQuizQuestions(questionCount)

        assertEquals(questions.size, randomQuestions.size)
    }

    @Test
    fun `test getRandomQuizQuestions with empty question list`() {
        val questionCount = 5
        `when`(questionRepository.getAllQuestions()).thenReturn(emptyList())

        val randomQuestions = createQuizUseCase.getRandomQuizQuestions(questionCount)

        assertTrue(randomQuestions.isEmpty())
    }

    // Add more test cases for different scenarios and edge cases

    // ...

}
