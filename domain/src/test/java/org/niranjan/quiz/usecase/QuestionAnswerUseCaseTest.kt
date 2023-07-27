package org.niranjan.quiz.usecase

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.niranjan.quiz.modal.AnswerEntity
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.result.AnswerResult

class QuestionAnswerUseCaseTest {

    @Mock
    private lateinit var mockQuizRepository: QuizRepository

    @Mock
    private lateinit var mockQuestionRepository: QuestionRepository

    @Mock
    private lateinit var mockScoreRepository: ScoreRepository

    private lateinit var questionAnswerUseCase: QuestionAnswerUseCase
    
    private lateinit var question: QuestionEntity
    private lateinit var quiz: QuizEntity

    private lateinit var questions :List<QuestionEntity>

    private lateinit var lastQuestionsOnly : MutableList<QuestionEntity>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        questionAnswerUseCase = QuestionAnswerUseCase(
            mockQuizRepository,
            mockQuestionRepository,
            mockScoreRepository
        )
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
        val fakeQuestion3  = QuestionEntity(
            questionId = "3",
            text = "What is the fuck are you ?",
            answers = listOf(fakeAnswer1, fakeAnswer2, fakeAnswer3),
            difficultyLevel = 1,
            category = "Science",
            correctAnswer = fakeAnswer3,
            isAnswered = false
        )
        question = fakeQuestion1
        questions = listOf(fakeQuestion1,fakeQuestion2)
        quiz = QuizEntity("1", "11",
            questions,
            emptyList(),
            0,
            0,
            false)
        lastQuestionsOnly = mutableListOf(fakeQuestion3)
    }

    @Test
    fun `test getFirstQuestion`() {
        // Mock data
         // done on setup 

        // Mocking the quiz repository to return the quiz
        `when`(mockQuizRepository.getCurrentQuiz()).thenReturn(quiz)

        // Call the method
        val result = questionAnswerUseCase.getFirstQuestion()

        // Verify
        assertTrue(result is AnswerResult.Success)
        assertTrue(questions.contains((result as AnswerResult.Success).question))
    }

    @Test
    fun `test answerQuestionAndGetNext with correct answer`() {
        // Mock data
        val questionId = "1"

        // Mocking the quiz repository to return the quiz
        `when`(mockQuizRepository.getCurrentQuiz()).thenReturn(quiz)

        // Call the method with correct answer
        val result = questionAnswerUseCase.answerQuestionAndGetNext(questionId, true)

        // Verify
        assertTrue(result is AnswerResult.Success)
        assertNotNull((result as AnswerResult.Success).question)
    }

    @Test
    fun `test answerQuestionAndGetNext with incorrect answer`() {
        // Mock data
        val questionId = "1"

        // Mocking the quiz repository to return the quiz
        `when`(mockQuizRepository.getCurrentQuiz()).thenReturn(quiz)

        // Call the method with incorrect answer
        val result = questionAnswerUseCase.answerQuestionAndGetNext(questionId, false)

        // Verify
        assertTrue(result is AnswerResult.Success)
        assertNotNull((result as AnswerResult.Success).question)
    }

    @Test
    fun `test answerQuestionAndGetNext with invalid questionId`() {
        // Mock data
        val questionId = "999" // Invalid questionId, not present in the quiz

        // Mocking the quiz repository to return the quiz
        `when`(mockQuizRepository.getCurrentQuiz()).thenReturn(quiz)


        // Call the method with incorrect questionId
        val result = questionAnswerUseCase.answerQuestionAndGetNext(questionId, true)

        // Verify
        assertTrue(result is AnswerResult.Failure)
    }
    @Test
    fun `test getNextQuestion when it's the last question`() {
        // Mock data done in set up
        questions.forEach()
        {
            it.isAnswered = true
            lastQuestionsOnly.add(it)
        }
        // Mocking the quiz repository to return the quiz
        `when`(mockQuizRepository.getCurrentQuiz()).thenReturn(quiz.copy(questions=lastQuestionsOnly))

        // Mark the first question as answered
         println( mockQuizRepository.getCurrentQuiz())

        // Call the method to get the next question (last question)
        val result = questionAnswerUseCase.answerQuestionAndGetNext("3", true)

        // Verify
        assertTrue(result is AnswerResult.Success)
        assertNull((result as AnswerResult.Success).question)
        assertTrue(result.isLastQuestion)
    }
}
