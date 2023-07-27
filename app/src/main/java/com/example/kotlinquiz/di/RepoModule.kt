package com.example.kotlinquiz.di

import android.content.Context
import com.example.kotlinquiz.local.dao.QuestionDao
import com.example.kotlinquiz.local.dao.QuizDao
import com.example.kotlinquiz.local.dao.ScoreDao
import com.example.kotlinquiz.local.dao.UserDao
import com.example.kotlinquiz.repository.QuestionRepoImpl
import com.example.kotlinquiz.repository.QuizRepoImpl
import com.example.kotlinquiz.repository.ScoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.repo.UserRepository


@Module
@InstallIn(SingletonComponent::class)
class RepoModule {


    @Provides
    fun provideQuizRepo( quizDao: QuizDao):QuizRepository  = QuizRepoImpl (quizDao)

    @Provides
    fun provideuestionRepo ( questionDao: QuestionDao, context: Context) : QuestionRepository
     = QuestionRepoImpl(questionDao,context)


    @Provides
    fun probidesScoreRepo( scoreDao: ScoreDao) :ScoreRepository= ScoreRepositoryImpl(scoreDao)

}