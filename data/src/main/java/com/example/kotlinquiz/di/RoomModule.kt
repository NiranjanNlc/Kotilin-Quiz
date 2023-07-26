package com.example.kotlinquiz.di

import android.app.Application
import android.content.Context
import com.example.kotlinquiz.local.AppDatabase
import com.example.kotlinquiz.local.dao.QuestionDao
import com.example.kotlinquiz.local.dao.QuizDao
import com.example.kotlinquiz.local.dao.ScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideDataBase(context: Context) : AppDatabase {
         return  AppDatabase.getInstance(context )
    }
    @Provides
    fun provideQuizDao(appDatabase: AppDatabase): QuizDao = appDatabase.quizDao()

    @Provides
    fun provideQuestionDao(appDatabase: AppDatabase): QuestionDao = appDatabase.questionDao()

    @Provides
    fun provideScoreDao(appDatabase: AppDatabase): ScoreDao = appDatabase.scoreDao()


}