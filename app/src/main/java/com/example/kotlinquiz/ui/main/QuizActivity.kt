package com.example.kotlinquiz.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlinquiz.R
import com.example.kotlinquiz.ui.main.fragment.QuizFragment
import com.example.kotlinquiz.ui.main.fragment.WelcomeFragment
import com.example.kotlinquiz.ui.main.util.QuizState
import com.example.kotlinquiz.ui.main.viewmodal.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class QuizActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

}