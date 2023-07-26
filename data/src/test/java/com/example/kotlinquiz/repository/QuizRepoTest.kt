package com.example.kotlinquiz.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.kotlinquiz.local.AppDatabase
import com.example.kotlinquiz.local.entity.Quiz
import com.example.kotlinquiz.local.dao.QuizDao
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.niranjan.quiz.modal.AnswerEntity
import org.niranjan.quiz.modal.QuestionEntity
import org.niranjan.quiz.modal.QuizEntity
import org.niranjan.quiz.modal.ScoreEntity
import org.niranjan.quiz.modal.UserEntity
import org.niranjan.quiz.repo.QuizRepository
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class QuizRepoTest {

    private lateinit var quizRepository: QuizRepository

    private lateinit var quizDatabase: AppDatabase

    private lateinit var quizDao: QuizDao

    private lateinit var questions :List<QuestionEntity>

    private lateinit var user : UserEntity

    private lateinit var quiz : QuizEntity

    @Before
    fun setUp() {
        // Use an in-memory Room database for testing
        val context = ApplicationProvider.getApplicationContext<Context>()
        quizDatabase =  Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // Add this line to allow running tests on the main thread
            .build()
        quizDao = quizDatabase.quizDao()
        quizRepository = QuizRepoImpl(quizDao)
        user = UserEntity("1", "Niranjan" )
        prepareFakeQuestion()
        quiz = preoarequizEntity1(false)
    }

    private fun preoarequizEntity(stautus: Boolean): Quiz {
        return Quiz(
            id = "1",
            userId = user.userId,
            questions = questions,
            scores = listOf(ScoreEntity("1", "1", "k", 1, 1L)),
            startTime = 0,
            duration = 0,
            isFinished = stautus
        )
    }
    private fun preoarequizEntity1(stautus: Boolean): QuizEntity {
        return QuizEntity(
            id = "1",
            userId = user.userId,
            questions = questions,
            scores = listOf(ScoreEntity("1", "1", "k", 1, 1L)),
            startTime = 0,
            duration = 0,
            isFinished = stautus
        )
    }

    @After
    fun tearDown() {
        quizDatabase.close()
    }

    @Test
    fun testCreateQuiz() {
        val createdQuiz = quizRepository.createQuiz(quiz)
        assertNotNull(createdQuiz)
        assertEquals(quiz.userId, createdQuiz?.userId)
        assertEquals(quiz.isFinished, createdQuiz?.isFinished)
    }

    @Test
    fun testUpdateQuiz() {
        // Create a quiz
        quizRepository.createQuiz(quiz)

        val updatedQuiz =  quiz.copy(isFinished = true)
        quizRepository.updateQuiz(updatedQuiz)
        val retrievedQuiz = quizRepository.getQuizById(quiz.id)
        assertNotNull(retrievedQuiz)
        assertEquals(updatedQuiz.isFinished, retrievedQuiz?.isFinished)
    }
//
//    @Test
//    fun testDeleteQuiz() {
//        val quiz = QuizEntity(id = "1", title = "Math Quiz")
//        quizRepository.createQuiz(quiz)
//
//        quizRepository.deleteQuiz("1")
//
//        val retrievedQuiz = quizRepository.getQuizById("1")
//        assertNull(retrievedQuiz)
//    }
//
//    @Test
//    fun testGetQuizById() {
//        val quiz = QuizEntity(id = "1", title = "Math Quiz")
//        quizRepository.createQuiz(quiz)
//
//        val retrievedQuiz = quizRepository.getQuizById("1")
//        assertNotNull(retrievedQuiz)
//        assertEquals(quiz.title, retrievedQuiz?.title)
//    }
//
//    @Test
//    fun testGetQuizzesByUser() {
//        val user1Quiz = QuizEntity(id = "1", title = "Math Quiz")
//        val user2Quiz = QuizEntity(id = "2", title = "Science Quiz")
//
//        quizRepository.createQuiz(user1Quiz)
//        quizRepository.createQuiz(user2Quiz)
//
//        val quizzesForUser1 = quizRepository.getQuizzesByUser("user1")
//        assertEquals(1, quizzesForUser1.size)
//        assertEquals(user1Quiz.title, quizzesForUser1[0].title)
//
//        val quizzesForUser2 = quizRepository.getQuizzesByUser("user2")
//        assertEquals(1, quizzesForUser2.size)
//        assertEquals(user2Quiz.title, quizzesForUser2[0].title)
//    }
//
//    @Test
//    fun testGetQuizScores() {
//        val quiz = QuizEntity(id = "1", title = "Math Quiz")
//        quizRepository.createQuiz(quiz)
//
//        val score1 = ScoreEntity(userId = "user1", score = 80)
//        val score2 = ScoreEntity(userId = "user2", score = 90)
//        val score3 = ScoreEntity(userId = "user3", score = 75)
//
//        quizRepository.getQuizScores("1").apply {
//            assertEquals(0, size)
//        }
//
//        quizRepository.addScore("1", score1)
//        quizRepository.addScore("1", score2)
//        quizRepository.addScore("1", score3)
//
//        quizRepository.getQuizScores("1").apply {
//            assertEquals(3, size)
//            assertTrue(contains(score1))
//            assertTrue(contains(score2))
//            assertTrue(contains(score3))
//        }
//    }
    private fun prepareFakeQuestion() {
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
}
