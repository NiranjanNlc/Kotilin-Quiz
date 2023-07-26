package com.example.kotlinquiz.ui.main.fragment



import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.kotlinquiz.R
import com.example.kotlinquiz.databinding.FragmentWelcomeBinding
import com.example.kotlinquiz.launchFragmentInHiltContainer
import com.example.kotlinquiz.ui.main.viewmodal.WelcomeViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class WelcomeFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    lateinit var binding: FragmentWelcomeBinding
    lateinit var viewModel: WelcomeViewModel
    @Before
    fun setup() {
        hiltRule.inject()
        Log.i("@Test", "Performing click on start setup  button")
        // Launch the WelcomeFragment
       launchFragmentInHiltContainer<WelcomeFragment>{
           binding = this.binding
           viewModel = this.viewModel
       }

    }

    @Test
    fun clickButton_navigatesToQuiz() {
        Log.i("@Test", "Performing click on start button")
        // Perform a click action on the button
        onView(withId(binding.btnStart.id)).perform(click())
        Log.i("@Test", "Checking if QuizFragment is visible")

        // Verify that the next fragment is  visible
        onView(withId(R.id.quizFragment)).check(matches(isDisplayed()))
    }
}
