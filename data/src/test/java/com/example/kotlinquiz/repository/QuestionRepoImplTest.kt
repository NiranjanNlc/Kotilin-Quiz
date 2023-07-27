package com.example.kotlinquiz.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.kotlinquiz.local.AppDatabase
import com.example.kotlinquiz.local.dao.QuestionDao
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.niranjan.quiz.modal.AnswerEntity
import org.niranjan.quiz.modal.QuestionEntity
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class QuestionRepoImplTest {

    private lateinit var questionRepo: QuestionRepoImpl
    @Mock
    private lateinit var questionDao: QuestionDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        questionDao = appDatabase.questionDao()
        questionRepo = QuestionRepoImpl(questionDao, context)
    }

    @After
    fun teardown() {
        appDatabase.close()
    }
    private fun createSampleQuestionEntity(
        questionId: String = "question_id",
        text: String = "Sample Question",
        category: String = "Sample Category"
    ): QuestionEntity {
        // Return a sample QuestionEntity for testing
        return QuestionEntity(
            questionId,
            text,
            emptyList(), // Add sample answers here if needed
            1, // Sample difficulty level
            category,
            null, // Sample correctAnswer if needed
            false // Sample isAnswered value
        )
    }
    @Test
    fun testCreateQuestion_Success() {
        val questionEntity = createSampleQuestionEntity()
        questionRepo.createQuestion(questionEntity)
        // Additional assertions to check if the question was inserted successfully
        val allQuestions = questionDao.getAllQuestion()
        assertEquals(1, allQuestions.size)
        assertEquals(questionEntity.questionId, allQuestions[0].questionId)
    }


    @Test
    fun testGetAllQuestions() {
     val listofqs = questionRepo.putallQuestion()
     val firstQuestion = QuestionEntity(
            questionId = "q1",
            text = "What is a nullable type in Kotlin?",
            answers = listOf(
                AnswerEntity("q1a1", "q1", "A type that cannot hold any value", false),
                AnswerEntity("q1a2", "q1", "A type that can hold null as a value", true),
                AnswerEntity("q1a3", "q1", "A type that is used for loops", false)
            ),
            difficultyLevel = 1,
            category = "Basics",
            correctAnswer = AnswerEntity("q1a2", "q1", "A type that can hold null as a value", true),
            isAnswered = false
        )
    //    verify the     first element
          assertEquals(listofqs[0],firstQuestion)
        //verify the last elements
         var lastqs = QuestionEntity(
            questionId = "q20",
            text = "What is the purpose of the 'with' function in Kotlin?",
            answers = listOf(
                AnswerEntity("q20a1", "q20", "Performing an operation with null safety", false),
                AnswerEntity("q20a2", "q20", "Performing multiple operations with a single object", true),
                AnswerEntity("q20a3", "q20", "Performing a type conversion", false)
            ),
            difficultyLevel = 2,
            category = "Functions",
            correctAnswer = AnswerEntity("q20a2", "q20", "Performing multiple operations with a single object", true),
            isAnswered = false
        )

        assertEquals(listofqs[19],lastqs)
    }


}
