package com.example.kotlinquiz.ui.main.util

// QuizPrefsHelper.kt (App Module)
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class QuizPrefsHelper @Inject constructor (private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)

    fun saveQuizStatus(isQuizStarted: Boolean,quizId: String) {
        sharedPreferences.edit()
            .putBoolean(KEY_QUIZ_STARTED, isQuizStarted)
            .apply()
        sharedPreferences.edit()
            .putString(KEY_QUIZ_ID,quizId)
            .apply()

    }

    fun getQuizStatus(): Boolean {
        return sharedPreferences.getBoolean(KEY_QUIZ_STARTED, false)
    }

    fun getQuizId(): String? {
         return  sharedPreferences.getString(KEY_QUIZ_ID,"74554tyw98e84949")
    }

    companion object {
        private const val KEY_QUIZ_STARTED = "is_quiz_started"
        private const val KEY_QUIZ_ID = "quiz_id"
    }
}
